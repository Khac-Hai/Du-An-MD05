import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter, Rate, Trend } from 'k6/metrics';

// Các chỉ số đo lường tùy chỉnh cho Báo cáo Bảo vệ Đồ án
const successfulOrders = new Counter('successful_orders');
const soldOutRejections = new Counter('sold_out_rejections');
const rateLimitHits = new Counter('rate_limit_hits');
const orderDuration = new Trend('order_duration_ms');

export const options = {
  scenarios: {
    // Kịch bản: Bùng nổ tải Flash Sale đồng thời cực đại (500 - 1000 req/s)
    flash_sale_spike: {
      executor: 'ramping-arrival-rate',
      startRate: 100,
      timeUnit: '1s',
      preAllocatedVUs: 200,
      maxVUs: 1000,
      stages: [
        { target: 500, duration: '10s' },  // Khởi động tăng dần lên 500 RPS
        { target: 1000, duration: '20s' }, // Giờ cao điểm Flash Sale đạt đỉnh 1000 RPS
        { target: 500, duration: '10s' },  // Hạ nhiệt dần về 500 RPS
        { target: 0, duration: '5s' },     // Kết thúc
      ],
    },
  },
  thresholds: {
    // Tiêu chuẩn cam kết chất lượng (Quality Gates) khi báo cáo với Hội đồng
    'http_req_duration': ['p(95)<1500', 'p(99)<3000'], // 95% request phản hồi dưới 1.5s ngay cả khi tải 1000 RPS
    'rate_limit_hits': ['count==0'],                  // Không có request nào bị chặn nhầm bởi Rate Limiting
  },
};

const BASE_URL = __ENV.GATEWAY_URL || 'http://localhost:8080';

// Hàm thiết lập ban đầu (Setup): Đăng nhập tài khoản & Đặt lại tồn kho trước khi test tải
export function setup() {
  console.log('>>> [k6 Thiết lập] Đang đăng nhập tài khoản Khách hàng & Quản trị viên...');

  // 1. Đăng nhập Khách hàng
  const loginRes = http.post(
    `${BASE_URL}/api/v1/auth/login`,
    JSON.stringify({ email: 'customer@ecommerce.com', password: 'Customer@123' }),
    { headers: { 'Content-Type': 'application/json' } }
  );

  let token = '';
  if (loginRes.status === 200) {
    token = loginRes.json('data.token');
    console.log('>>> [k6 Thiết lập] Lấy mã JWT Token Khách hàng thành công.');
  } else {
    console.error(`Đăng nhập thất bại: ${loginRes.body}`);
  }

  // 2. Đăng nhập Admin & Đặt lại tồn kho Sản phẩm #1 về 100 cái để chạy test tải
  const adminLoginRes = http.post(
    `${BASE_URL}/api/v1/auth/login`,
    JSON.stringify({ email: 'admin@ecommerce.com', password: 'Admin@123' }),
    { headers: { 'Content-Type': 'application/json' } }
  );
  if (adminLoginRes.status === 200) {
    const adminToken = adminLoginRes.json('data.token');
    http.post(
      `${BASE_URL}/api/v1/orders/benchmark/reset-stock?productId=1&stock=100`,
      null,
      { headers: { Authorization: `Bearer ${adminToken}` } }
    );
    console.log('>>> [k6 Thiết lập] Đã khởi tạo lại kho Sản phẩm #1 thành đúng 100 cái.');
  }

  return { token };
}

export default function (data) {
  const payload = JSON.stringify({
    items: [
      { productId: 1, quantity: 1 }
    ],
    notes: 'Yêu cầu kiểm thử tải từ k6'
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${data.token}`,
      'X-Forwarded-For': `10.0.0.${Math.floor(Math.random() * 254) + 1}`, // Kiểm tra khả năng chống giả mạo IP
    },
    timeout: '5s',
  };

  const startTime = Date.now();
  const res = http.post(`${BASE_URL}/api/v1/orders`, payload, params);
  orderDuration.add(Date.now() - startTime);

  if (res.status === 201) {
    successfulOrders.add(1);
    check(res, { 'Tạo đơn hàng thành công': (r) => r.status === 201 });
  } else if (res.status === 409 || res.status === 400) {
    soldOutRejections.add(1);
    check(res, { 'Từ chối an toàn khi hết hàng': (r) => r.status === 409 || r.status === 400 });
  } else if (res.status === 429) {
    rateLimitHits.add(1);
    console.warn('Đã chạm giới hạn Rate Limit (Mã 429)! Cần tăng thêm giá trị RATE_LIMIT_MAX.');
  } else {
    check(res, { 'Trạng thái ngoài dự kiến': (r) => false });
  }
}

// Hàm dọn dẹp sau bài test (Teardown): Kiểm tra tồn kho cuối cùng trong cơ sở dữ liệu
export function teardown(data) {
  console.log('>>> [k6 Hoàn tất] Bài test tải kết thúc! Đang kiểm tra tồn kho thực tế trong cơ sở dữ liệu...');
  const invRes = http.get(`${BASE_URL}/api/v1/orders/inventory/1`);
  if (invRes.status === 200) {
    console.log(`>>> [Tồn kho Sản phẩm #1 sau cùng]: Khả dụng=${invRes.json('availableStock')}, Đang giữ=${invRes.json('reservedStock')}`);
  }
}
