displayDelay <- function (filename,user = 0, speed = 0) {
  if (user == 0 & speed == 0) {
    setwd ("/Users/qdang/workspace/collborative_editing_measurement")
    df <- read.table (filename, header = TRUE)
    df$delay <- df$delay / 1000
    u <- df$user
    s <- df$speed
    us <- paste(formatC( u , width = 2, format = "d", flag = "0") , '.' , formatC( s , width = 2, format = "d", flag = "0" ), space='')
    boxplot (df$delay~us, las = 2, ylab = "Delay (seconds)", main = "Google Docs performance")
  }
  if (user == 0) {
    setwd ("/Users/qdang/workspace/collborative_editing_measurement")
    df <- read.table (filename, header = TRUE)
    df$delay <- df$delay / 1000
    df <- df [df$speed == speed,]
    u <- df$user
    s <- df$speed
    us <- paste(formatC( u , width = 2, format = "d", flag = "0") , '.' , formatC( s , width = 2, format = "d", flag = "0" ), space='')
    boxplot (df$delay~us, las = 2, ylab = "Delay (seconds)", main = "Google Docs performance")
  }
  if (speed == 0) {
    setwd ("/Users/qdang/workspace/collborative_editing_measurement")
    df <- read.table (filename, header = TRUE)
    df$delay <- df$delay / 1000
    df <- df[df$user == user, ]
    u <- df$user
    s <- df$speed
    us <- paste(formatC( u , width = 2, format = "d", flag = "0") , '.' , formatC( s , width = 2, format = "d", flag = "0" ), space='')
    boxplot (df$delay~us, las = 2, ylab = "Delay (seconds)", main = "Google Docs performance")
  }
  else {
    setwd ("/Users/qdang/workspace/collborative_editing_measurement")
    df <- read.table (filename, header = TRUE)
    df$delay <- df$delay / 1000
    df <- df[df$user == user & df$speed == speed,]
    u <- df$user
    s <- df$speed
    us <- paste(formatC( u , width = 2, format = "d", flag = "0") , '.' , formatC( s , width = 2, format = "d", flag = "0" ), space='')
    boxplot (df$delay~us, las = 2, ylab = "Delay (seconds)", main = "Google Docs performance")
  }
}

try_regression <- function (file_name, speed=1, poly = 3, x_step=2)
{
  #setwd ("/Users/qdang/workspace/collborative_editing_measurement")
  plot.new()
  df <- read.table (file_name, header = TRUE)
  df$delay <- df$delay / 1000
  df <- df [df$speed == speed,]
  means <- tapply (df$delay, df$user, mean)
  unique(df$user)
  lm <- lm (means ~ poly (unique(df$user), 3))
  boxplot (df$delay ~ df$user,ylab="Delay in seconds", xlab="Number of user", 
           main=paste("Google Docs performance with typing speed = ", speed), las=2)
  #axis(side=1,at=seq(0,50,by=x_step),las=2)
  #lines (unique (df$user), predict (lm), col="red")
  lines (predict(lm), col = "red")
  lm
}

processAllGoogleDocs <- function (file_name, poly = 3, x_step = 2) {
  df <- read.table (file_name, header = TRUE)   #read data
  df$delay <- df$delay / 1000 #convert to seconds
  speeds <- c (1,2,4,6,8,10)
  for (speed in speeds) {
    model = try_regression (file_name = file_name, speed = speed)
    summary(model)
  }
}