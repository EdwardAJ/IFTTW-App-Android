# IF3111-2020-11-IFTTW

### 1. Deskripsi aplikasi IFTTW
    IFTTW adalah aplikasi pada platform android yang memungkinkan pengguna untuk membuat reminder atau notifier sesuai dengan kondisi aktivasi yang diinginkan. Seperti namanya, aplikasi If This Then What akan melakukan sebuah aksi yang telah didefinisikan jika kondisi yang bersangkutan telah terpenuhi
    Kondisi yang kami implementasi adalah:
    * Time based activation (alarm)
    * Keyword detection from API activation
    * Sensor input activation

    Sementara untuk aksi yang dapat dilakukan adalah:
    * Mengirim notifikasi
    * Mengaktifkan WiFi
    * Menonaktifkan WiFi

### 2. Cara kerja aplikasi
    Saat aplikasi dijalankan, maka akan ditampilkan Halaman dengan 2 tab yaitu Active Routine dan Inactive Routine (Gambar 1 dan 2). Di sudut kanan bawah terdapat tanda plus untuk menambah routine yang jika ditekan akan menampilkan halaman Add Routine yang berisi 3 tab yang berkoresponden dengan 3 kondisi aktivasi IFTTW yaitu Timer, News, dan Sensor(Gambar 3, 4, dan 5). 

    Timer menggunakan alarm untuk menjalankan aksi yang didefinisikan. Terdapat 2 cara penggunaan timer yaitu dengan mengisi kolom date dan tanpa mengisi kolom date. Dengan mengisi kolom date maka hanya akan ada 1 aksi yang dilakukan sementara jika tidak diisi, maka alarm akan bersifat repetisi dengan pengulangan sesuai pilihan repeat yaitu Everyday (daily alarm) ataupun Every <day_name> (weekly alarm).

    News menggunakan request ke API pada newsapi.com dengan memeriksa keyword sesuai input pengguna sebagai kondisi aktivasi untuk penjalanan aksi IFTTW.

    Sensor, dalam implementasi kami dengan sensor cahaya akan memicu aksi jika cahaya yang diterima sensor lebih dari jumlah input dalam satuan Lumen.

### 3. Library yang digunakan
    Kami tidak menggunakan library dalam pengerjaan tugas ini.
### 4. Screenshot aplikasi
 Gambar 1

![Gambar 1](/images/1.jpg)

 Gambar 2

![Gambar 2](/images/1.jpg)

 Gambar 3

![Gambar 3](/images/1.jpg)

 Gambar 4

![Gambar 4](/images/1.jpg)

 Gambar 5

![Gambar 5](/images/1.jpg)

 Gambar 6

![Gambar 6](/images/1.jpg)

 Gambar 7

![Gambar 7](/images/1.jpg)

 Gambar 8

![Gambar 8](/images/1.jpg)

 Gambar 9

![Gambar 9](/images/1.jpg)

 Gambar 10

![Gambar 10](/images/1.jpg)

 Gambar 11

![Gambar 11](/images/1.jpg)
