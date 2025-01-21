# 建構 Docker Image

# 1.使用 openjdk 17 openjdk image
FROM openjdk:17

# 2.建立程式資料夾(指的是在容器裡面，非實體主機目錄上)
RUN mkdir /app

# 3.將可執行的應用程式(.jar)複製到容器的工作目錄(/app內)
COPY target/calibration.jar /app

# 4.設定執行工作目錄
WORKDIR /app

# 5.執行應用程式
CMD ["java","-jar","calibration.jar"]