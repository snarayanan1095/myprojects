#!/usr/bin/env python

"""
Given an integer array arr, count element x such that x + 1 is also in arr.

If there're duplicates in arr, count them seperately.
"""
def countElements(arr):
    c=0
    for a in arr:
       if a+1 in arr:
           c+=1
    return c

print countElements([1,3,2,3,5,0])

