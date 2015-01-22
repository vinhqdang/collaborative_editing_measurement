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