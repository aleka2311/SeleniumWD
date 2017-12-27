set SERVER_VERSION=3.8.1
set HUB_PORT=4444
set NODE_PORT=5556
set REGISTER_IP=localhost
set CHROME_DRIVER=chromedriver.exe
java -Dwebdriver.chrome.driver="%CHROME_DRIVER%" -jar selenium-server-standalone-%SERVER_VERSION%.jar -role node -hub http://%REGISTER_IP%:%HUB_PORT%/grid/register -browser "browserName=chrome,platform=WINDOWS" -port %NODE_PORT%