# SmartCommerce-Pro

啟動 Nacos 服務器：
打開終端，進入 Nacos 解壓後的目錄
啟動 Nacos（單機模式）：
對於 Linux/Mac：
cd bin
sh startup.sh -m standalone
驗證 Nacos 是否正常運行：
http://localhost:8848/nacos/
關閉nacos：
sh shutdown.sh

啟動配置及網關服務：
mvn clean package
java -jar target/config-service-0.0.1-SNAPSHOT.jar
mvn clean package
java -jar target/api-gateway-0.0.1-SNAPSHOT.jar


1. 啟動 MySQL 服務
確保 MySQL 服務正在運行：
macOS: brew services start mysql


2. 連接到 MySQL
您可以通過命令行或圖形界面工具連接到 MySQL：
命令行方式：
mysql -u root -p
系統會提示您輸入 root 用戶的密碼。如果您是首次安裝 MySQL，可能需要先設置 root 密碼。
圖形界面工具：
您也可以使用 MySQL Workbench、DBeaver、DataGrip 等圖形化工具連接資料庫。
3. 執行 SQL 腳本
連接到 MySQL 後，複製並執行以下 SQL 腳本：


離開時: 
EXIT;