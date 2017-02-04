sudo apt-get update
sudo DEBIAN_FRONTEND=noninteractive apt-get --yes install openjdk-8-jdk openjdk-8-demo openjdk-8-doc openjdk-8-jre-headless openjdk-8-source
sudo DEBIAN_FRONTEND=noninteractive apt-get --yes install unzip
sudo DEBIAN_FRONTEND=noninteractive apt-get --yes install xvfb
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo dpkg -i google-chrome-stable_current_amd64.deb
wget http://chromedriver.storage.googleapis.com/2.27/chromedriver_linux64.zip
sudo apt-get -f --yes install
unzip chromedriver_linux64.zip
sudo chmod u+x chromedriver
sudo mv chromedriver /usr/bin/
sudo -s
echo "CHROME_BIN=/usr/bin/google-chrome" >> /etc/environment
echo "DISPLAY=:99.0" >> /etc/environment
#to avoid chrome hanging
echo "DBUS_SESSION_BUS_ADDRESS=/dev/null" >> /etc/environment
exit

curl http://selenium-release.storage.googleapis.com/3.0/selenium-server-standalone-3.0.1.jar -o selenium-server-standalone-3.0.1.jar
sudo chmod 777 selenium-server-standalone-3.0.1.jar
	
/sbin/start-stop-daemon --start --quiet --pidfile /tmp/custom_xvfb_99.pid --make-pidfile --background --exec /usr/bin/Xvfb -- :99 -ac -screen 0 1280x1024x16
java -jar selenium-server-standalone-3.0.1.jar &
