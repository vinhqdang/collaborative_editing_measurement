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

try_regression <- function (file_name, speed=1, poly = 3, x_step=2, max_y = 60, max_x = 50)
{
  setwd ("/Users/qdang/workspace/collborative_editing_measurement")
  df <- read.table (file_name, header = TRUE)
  df$delay <- df$delay / 1000
  df <- df [df$speed == speed,]
  means <- tapply (df$delay, df$user, mean)
  lm <- lm (means ~ poly (unique(df$user), poly))
  plot (df$delay ~ df$user,ylab="Delay in seconds", xlab="Number of user", 
        # main=paste("Google Docs performance with typing speed = ", speed), 
        las=2,
        ylim = c(0,max_y),
        xlim = c(1,max_x))
  axis(side=1,at=seq(0,50,by=x_step),las=2)
  #lines (unique (df$user), predict (lm), lwd =2)
  # print (summary (lm))
  
  # create a line between average
  lines(x = unique(df$user), y = means)
}