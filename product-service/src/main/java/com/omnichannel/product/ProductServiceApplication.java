package com.omnichannel.product;

import com.omnichannel.product.entity.Category;
import com.omnichannel.product.entity.Product;
import com.omnichannel.product.repository.CategoryRepository;
import com.omnichannel.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedCatalog(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Điện thoại thông minh", "smartphones", "Điện thoại cao cấp chính hãng"));
                categoryRepository.save(new Category("Laptop & Máy tính", "laptops", "Laptop doanh nhân, gaming"));
                categoryRepository.save(new Category("Tai nghe & Âm thanh", "audio", "Tai nghe chống ồn, loa Bluetooth"));
                categoryRepository.save(new Category("Phụ kiện công nghệ", "accessories", "Sạc, ốp lưng, đồng hồ"));
            }

            saveProduct(productRepository, "iPhone 16 Pro Max 256GB Desert Titanium (Flash Sale)", "IPHONE-16-PM-256", "iphone-16-pro-max-256gb",
                    "Siêu phẩm điện thoại cao cấp nhất của Apple với chip A18 Pro, nút Camera Control và khung viền Titan siêu nhẹ. Dành cho sự kiện Flash Sale chịu tải cao!",
                    new BigDecimal("34990000"), new BigDecimal("37990000"), 10, "smartphones",
                    "https://images.unsplash.com/photo-1695048133142-1a20484d2569?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("19990000"));

            saveProduct(productRepository, "Samsung Galaxy S24 Ultra 512GB Titanium Gray", "SAMS-S24U-512", "samsung-galaxy-s24-ultra",
                    "Galaxy AI quyền năng, camera 200MP zoom quang 5x cùng bút S-Pen tích hợp.",
                    new BigDecimal("31990000"), new BigDecimal("35490000"), 50, "smartphones",
                    "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("27990000"));

            saveProduct(productRepository, "MacBook Pro 14 M3 Pro 18GB/512GB Space Black", "MBP-14-M3PRO", "macbook-pro-14-m3-pro",
                    "Đỉnh cao hiệu năng đồ họa và lập trình với chip Apple M3 Pro, màn hình Liquid Retina XDR 120Hz.",
                    new BigDecimal("49990000"), new BigDecimal("52990000"), 25, "laptops",
                    "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&w=800&q=80", false, null);

            saveProduct(productRepository, "Sony WH-1000XM5 Chống Ồn Đầu Bảng", "SONY-WH1000XM5", "sony-wh-1000xm5",
                    "Chống ồn chủ động hàng đầu ngành, thời lượng pin 30 giờ, âm thanh Hi-Res LDAC.",
                    new BigDecimal("7990000"), new BigDecimal("8990000"), 100, "audio",
                    "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("6490000"));

            saveProduct(productRepository, "Apple Watch Ultra 2 GPS + Cellular 49mm Titanium", "AW-ULTRA-2-49", "apple-watch-ultra-2",
                    "Đồng hồ thể thao bền bỉ nhất, định vị GPS kép chính xác, màn hình 3000 nits.",
                    new BigDecimal("21490000"), new BigDecimal("22990000"), 40, "accessories",
                    "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=800&q=80", false, null);

            saveProduct(productRepository, "iPad Pro M4 11 inch 256GB Wi-Fi Space Black", "IPAD-PRO-M4-11", "ipad-pro-m4-11-inch",
                    "Màn hình OLED Ultra Retina XDR đỉnh cao, độ mỏng chỉ 5.3mm với sức mạnh bứt phá từ chip Apple M4.",
                    new BigDecimal("27990000"), new BigDecimal("29990000"), 35, "laptops",
                    "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("24990000"));

            saveProduct(productRepository, "Bàn Phím Cơ Không Dây Keychron Q1 Pro QMK/VIA Carbon", "KEYCHRON-Q1-PRO", "ban-phim-co-keychron-q1-pro",
                    "Vỏ nhôm CNC nguyên khối, kết nối Bluetooth 5.1 & Type-C, hot-swap toàn bộ switch, hỗ trợ tùy biến QMK/VIA.",
                    new BigDecimal("4590000"), new BigDecimal("4990000"), 60, "accessories",
                    "https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=800&q=80", false, null);

            saveProduct(productRepository, "Chuột Công Thái Học Logitech MX Master 3S Pale Grey", "LOGI-MX-MASTER-3S", "chuot-logitech-mx-master-3s",
                    "Cảm biến 8000 DPI theo dõi trên mọi bề mặt kể cả mặt kính, nút bấm Quiet Clicks êm ái, cuộn siêu tốc MagSpeed.",
                    new BigDecimal("2490000"), new BigDecimal("2890000"), 80, "accessories",
                    "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("1990000"));

            saveProduct(productRepository, "Loa Di Động Cao Cấp Marshall Stanmore III Bluetooth", "MARSHALL-STANMORE-3", "loa-marshall-stanmore-iii",
                    "Chất âm sống động tràn ngập không gian phòng khách, thiết kế vintage hoài cổ đặc trưng của thương hiệu Marshall.",
                    new BigDecimal("9990000"), new BigDecimal("10990000"), 30, "audio",
                    "https://images.unsplash.com/photo-1545454675-3531b543be5d?auto=format&fit=crop&w=800&q=80", false, null);

            saveProduct(productRepository, "Tai Nghe Apple AirPods Pro 2 USB-C MagSafe", "AIRPODS-PRO-2-USBC", "apple-airpods-pro-2-usbc",
                    "Chống ồn chủ động gấp 2 lần, âm thanh thích ứng tự động điều chỉnh theo môi trường và chuẩn sạc Type-C hiện đại.",
                    new BigDecimal("5990000"), new BigDecimal("6490000"), 75, "audio",
                    "https://images.unsplash.com/photo-1600294037681-c80b4cb5b434?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("4890000"));

            saveProduct(productRepository, "Laptop Gaming Asus ROG Zephyrus G16 OLED Core Ultra 9", "ASUS-ROG-G16-OLED", "asus-rog-zephyrus-g16-oled",
                    "Màn hình OLED 2.5K 240Hz sắc nét đỉnh cao, trang bị Intel Core Ultra 9 185H và card đồ họa NVIDIA RTX 4080.",
                    new BigDecimal("69990000"), new BigDecimal("74990000"), 15, "laptops",
                    "https://images.unsplash.com/photo-1603302576837-37561b2e2302?auto=format&fit=crop&w=800&q=80", false, null);

            saveProduct(productRepository, "Màn Hình Chuyên Đồ Họa Dell UltraSharp 27 4K U2723QE", "DELL-U2723QE-4K", "man-hinh-dell-ultrasharp-27-4k",
                    "Tấm nền IPS Black độ tương phản 2000:1, chuẩn màu 98% DCI-P3, cổng kết nối USB-C Hub truyền dữ liệu và sạc 90W.",
                    new BigDecimal("13990000"), new BigDecimal("15490000"), 45, "accessories",
                    "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?auto=format&fit=crop&w=800&q=80", false, null);

            saveProduct(productRepository, "Xiaomi 14 Ultra 512GB Leica Photography Kit", "XIAOMI-14-ULTRA", "xiaomi-14-ultra-512gb",
                    "Hệ thống 4 camera quang học Leica cảm biến 1-inch biến thiên khẩu độ, quay video 8K chuyên nghiệp.",
                    new BigDecimal("28990000"), new BigDecimal("32990000"), 20, "smartphones",
                    "https://images.unsplash.com/photo-1598327105666-5b89351aff97?auto=format&fit=crop&w=800&q=80", true, new BigDecimal("23990000"));

            saveProduct(productRepository, "Tay Cầm Chơi Game Không Dây Sony DualSense Edge PS5", "SONY-DUALSENSE-EDGE", "tay-cam-ps5-dualsense-edge",
                    "Tay cầm Pro cao cấp cho game thủ với cần analog có thể thay thế, nút gạt phía sau tùy biến và hành trình cò có thể chỉnh.",
                    new BigDecimal("4990000"), new BigDecimal("5490000"), 50, "accessories",
                    "https://images.unsplash.com/photo-1600080972464-8e5f35f63d08?auto=format&fit=crop&w=800&q=80", false, null);

            System.out.println(">>> [ProductService] Seeded catalog with 14 products!");
        };
    }

    private void saveProduct(ProductRepository repo, String name, String sku, String slug,
                             String desc, BigDecimal price, BigDecimal origPrice, int stock,
                             String cat, String img, boolean flash, BigDecimal flashPrice) {
        if (repo.findBySku(sku).isEmpty()) {
            Product p = new Product();
            p.setName(name);
            p.setSku(sku);
            p.setSlug(slug);
            p.setDescription(desc);
            p.setPrice(price);
            p.setOriginalPrice(origPrice);
            p.setStock(stock);
            p.setCategorySlug(cat);
            p.setImageUrl(img);
            p.setFlashSale(flash);
            p.setFlashSalePrice(flashPrice);
            repo.save(p);
        }
    }
}
