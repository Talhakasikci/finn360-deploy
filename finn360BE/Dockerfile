#imajın temeli- uygulamanın çalışmasın için gereken java 17 jre ve apine linux dağıtımı
FROM eclipse-temurin:17-jre-alpine
#alpine linux üzerinde bir grup ve bir user oluşturma
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
#konteyner'ın içindeki varsayılan çalışma dizini
WORKDIR /app
#benim pc'dei .jar uzantılı dosyayı konteyner içindeki /app dizinine app.jar diye atar
COPY target/*.jar app.jar
#/app klasörünün ve içine kopyalanan uygulamanın sahipliğini (okuma/çalıştırma haklarını) root yöneticisinden alıp, yeni oluşturulan kısıtlı appuser kullanıcısına devredir.
RUN chown -R appuser:appgroup /app
#bu satırdan sonra uygulama en düşük yetki seviytesi ile çalışır
USER appuser
#konteyner iç ağda 8080
EXPOSE 8080
#Konteyner başlatıldığı milisaniyede çalıştırılacak olan değişmez ana komuttur. Java uygulamanı ayağa kaldırır.
ENTRYPOINT ["java", "-jar", "app.jar"]