#!/usr/bin/env python

def maxProfit(prices):
    """ 
    Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).

    Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
    """
    maxprofit = 0
    for i in range(0, len(prices)-1):
        ap = prices[i+1]-prices[i]
        if ap >0:
            maxprofit = maxprofit+ap
            
    return maxprofit

print maxProfit([7,1,5,3,6,4])
