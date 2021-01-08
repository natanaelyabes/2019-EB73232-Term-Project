library(readtext)
library(ggplot2)

# ===========================================================================================================/
# 
# @author: Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
#
# R Script for generating plot of GA Fitness for solving different single objective optimization problem
# comparing with various selection operator. 
#
# ===========================================================================================================/

absf <- function (fitnessDocs, type) {

  # Best so far matrix
  best_so_far <- matrix(1:5000, 10, 500)
  
  # Init dataframe
  avg_best_so_far <- data.frame("Documents" = 0, "Generations" = 0, "Fitness" = 0, "Type" = type)
  
  # 1st iterator
  i <- 0
  
  # For each fitness document
  for (fitnessDoc in fitnessDocs) {
    
    # Update 1st iterator 
    i <- i + 1
    
    # Split docs by enter
    fitnessDocs[i] <- strsplit(fitnessDoc, "\n")
    
    # 2nd iterator
    j <- 0
    
    for (val in fitnessDocs[i][[1]]) {
      
      # Update 2nd iterator
      j <- j + 1
      
      # Update vector
      best_so_far[i,j] <- as.double(fitnessDocs[i][[1]][j])
    }
  }
  
  # Reset iterator
  i <- 0
  i_doc <- 1
  i_gen <- 0
  
  # For each best_so_far fitness value
  for (fitness in c(t(best_so_far))) {
    
    # Update iterator
    i <- i + 1
    i_gen <- i_gen + 1
    
    # Set best so far fitness value
    avg_best_so_far[i, 1] <- i_doc
    avg_best_so_far[i, 2] <- i_gen
    avg_best_so_far[i, 3] <- fitness
    avg_best_so_far[i, 4] <- type
    
    # Label documents for every 500 generations
    if (i %% 500 == 0) {
      i_doc <- i_doc + 1
      i_gen <- 0
    }
  }
  
  return(avg_best_so_far)
}

plot <- function (avg_best_so_far, context) {
  avg_best_so_far$Generations <- factor(avg_best_so_far$Generations)
  ggplot(avg_best_so_far, aes(x = Generations, y = Fitness, fill = Type)) + 
    ggtitle(context) + 
    geom_boxplot() +
    theme(axis.text.x = element_text(angle= 45), plot.title = element_text(hjust = 0.5)) + coord_cartesian(xlim = c(1, 20), ylim = c(-1.2, 0))
}

# Read from folder and get fitness documents
SUS <- readtext::readtext("../results/KowalikFunction/SUS/*.txt")
BTS <- readtext::readtext("../results/KowalikFunction/BTS/*.txt")
RTS <- readtext::readtext("../results/KowalikFunction/RTS/*.txt")

fitnessSUSDocs <- SUS[,2]
fitnessBTSDocs <- BTS[,2]
fitnessRTSDocs <- RTS[,2]

absf_sus <- absf(fitnessSUSDocs, type = "SUS")
absf_bts <- absf(fitnessBTSDocs, type = "BTS")
absf_rts <- absf(fitnessRTSDocs, type = "RTS")

# SUS & BTS plot
absf <- rbind(absf_sus, absf_bts)
plot(absf, "Plot of GA Fitness for Solving Kowalik Function comparing SUS and BTS Selector")

# BTS & RTS plot
absf <- rbind(absf_bts, absf_rts)
plot(absf, "Plot of GA Fitness for Solving Kowalik Function comparing BTS and RTS Selector")
