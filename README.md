# Dockerfile
Chia quá trình build image thành 2 stage.
Stage 1: sử dụng image base có sẵn maven để đóng gói source code thành file jar.
Stage 2: sử dụng image base có sẵn jre để chạy file jar đã đóng gói từ stage 1.

# docker-compose.yml
Trên môi trường đã cài docker, tạo một thư mục để chứa 2 repo.
Clone 2 repo vào thư mục đã tạo, checkout đến nhánh cần build. Với 2 repo này là `develop`.  
Chỉ định context chạy docker compose là thư mục product_service, để khởi động ứng dụng, chạy lệnh `docker compose up -d`
