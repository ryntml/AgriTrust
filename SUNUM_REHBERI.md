# SUNUM REHBERİ - Reyyan Temel
## AgriTrust - Distributed Computing Project
### Görev Alanı: Database Design, Containerization, Documentation

---

# 1. GİRİŞ (30 saniye)

"Ben projenin **infrastructure** kısmından sorumluydum. Yani veritabanı tasarımı, containerization ve message queue sistemi."

---

# 2. POSTGRESQL MASTER-SLAVE REPLİKASYON

## Ne Yaptım:
- **Master Node** (Port: 5440) → Tüm yazma işlemleri
- **Slave Node** (Port: 5433) → Otomatik kopya, yedek

## Nasıl Çalışıyor:
```
[Yazma İsteği] → [MASTER] → [Otomatik Senkronizasyon] → [SLAVE]
                    ↓                                      ↓
              Aktif Sunucu                           Yedek Sunucu
```

## Neden Önemli:
- **High Availability** - Master çökerse Slave devreye girer
- **Fault Tolerance** - Veri kaybı önlenir
- **Load Balancing** - Okuma yükü dağıtılabilir

## Teknik Detay:
```yaml
postgres-master:
  environment:
    - POSTGRESQL_REPLICATION_MODE=master
    
postgres-slave:
  environment:
    - POSTGRESQL_REPLICATION_MODE=slave
    - POSTGRESQL_MASTER_HOST=postgres-master
```

---

# 3. RABBITMQ MESSAGE QUEUE

## Problem:
"Kullanıcı ürün oluşturduğunda loglama, email, analytics... Hepsi zaman alır."

## Çözüm:
"Ağır işlemleri kuyruğa at, kullanıcı hemen cevap alsın."

## Akış:
```
[API Request] → [DB Kaydet] → [Queue'ya At] → [Hızlı Response!]
                                    ↓
                          [Arka Planda İşle]
                                    ↓
                    Log / Email / Analytics (Async)
```

## Bileşenler:
| Bileşen | Dosya | Görev |
|---------|-------|-------|
| Producer | EventMessageProducer.java | Mesaj gönderir |
| Queue | agritrust.event.queue | Mesajları saklar |
| Consumer | EventMessageConsumer.java | Mesajları işler |

## Neden Önemli:
- **Prevent Request Overload** - 1000 istek gelirse kuyruğa alınır
- **Asenkron İşleme** - Kullanıcı beklemez
- **Hata Toleransı** - Consumer çökse bile mesajlar kaybolmaz

---

# 4. DOCKER CONTAINERIZATION

## Tek Komutla Başlatma:
```bash
docker-compose up --build -d
```

## 4 Container:
1. `postgres-master` - Ana veritabanı
2. `postgres-slave` - Yedek veritabanı
3. `rabbitmq` - Mesaj kuyruğu
4. `agritrust-backend` - Spring Boot API

## Dockerfile Özellikleri:
- Multi-stage build (Build + Runtime ayrımı)
- JRE Alpine (minimal image ~150MB)
- Non-root user (güvenlik)
- Health check endpoint

## Neden Docker:
- Tutarlı ortam ("Bende çalışıyor" sorunu yok)
- Kolay deployment
- Ölçeklenebilirlik

---

# 5. DEMO ADIMLARI

## Terminal Komutları:

### Servisleri Göster:
```powershell
docker-compose ps
```

### Kullanıcı Kaydet:
```powershell
curl.exe -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "@register.json"
```

### Giriş Yap:
```powershell
curl.exe -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "@login.json"
```

### Ürün Oluştur:
```powershell
curl.exe -X POST http://localhost:8080/api/product -H "Content-Type: application/json" -H "Authorization: Bearer TOKEN" -d "@product.json"
```

### RabbitMQ Loglarını Göster:
```powershell
docker-compose logs agritrust-backend | Select-String "Event message"
```

### Beklenen Çıktı:
```
✅ Event message sent to queue: HARVEST - BatchCode: XXXXXXXX
📥 Received event from queue
```

---

# 6. OLUŞTURDUĞUM DOSYALAR

| Dosya | Açıklama |
|-------|----------|
| docker-compose.yml | 4 servis tanımı |
| DockerFile | Multi-stage build |
| application.yml | DB + RabbitMQ config |
| RabbitMQConfig.java | Queue, Exchange tanımları |
| EventMessageDto.java | Mesaj formatı |
| EventMessageProducer.java | Mesaj gönderici |
| EventMessageConsumer.java | Mesaj dinleyici |
| README.md | Dokümantasyon |

---

# 7. ANAHTAR KELİMELER

| Terim | Anlamı |
|-------|--------|
| High Availability | Sistem her zaman erişilebilir |
| Fault Tolerance | Bir parça çökse bile sistem çalışır |
| Master-Slave Replication | Veritabanı otomatik kopyalama |
| Asenkron İşleme | Arka planda, kullanıcıyı bekletmeden |
| Message Queue | Mesaj kuyruğu, yük dengeleme |
| Containerization | Docker ile paketleme |

---

# 8. OLASI SORULAR

**S: Master çökerse ne olur?**
> Slave devreye girer, veri kaybı olmaz.

**S: RabbitMQ çökerse ne olur?**
> Mesajlar diske yazılıyor (durable), kaybolmaz.

**S: Neden Docker kullandınız?**
> Tutarlı ortam, kolay deployment, ölçeklenebilirlik.

**S: Neden Kafka değil RabbitMQ?**
> Kafka büyük sistemler için. Bizim ölçeğimizde RabbitMQ yeterli ve basit.

---

# ÖZET

Ben sistemin **altyapısını** hazırladım:
- ✅ PostgreSQL Master-Slave Replication
- ✅ RabbitMQ Message Queue
- ✅ Docker Containerization
- ✅ Health Monitoring (Actuator)
- ✅ Documentation

Bunlar **production-ready** bir sistem için gerekli temel bileşenler.
