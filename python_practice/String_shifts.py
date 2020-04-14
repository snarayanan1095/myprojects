#!/usr/bin/env python

"""
You are given a string s containing lowercase English letters, and a matrix shift, where shift[i] = [direction, amount]:

direction can be 0 (for left shift) or 1 (for right shift). 
amount is the amount by which string s is to be shifted.
A left shift by 1 means remove the first character of s and append it to the end.
Similarly, a right shift by 1 means remove the last character of s and add it to the beginning.
Return the final string after all operations.
"""

def stringShift(s, shift): 
    def shiftd(s, a,d):
        if d == 0:
            first = s[0:a]
            second = s[a:]
        else:
            first = s[0:len(s)-a]
            second = s[len(s)-a:]
        return second+first
            
        s_dict = {0:0, 1:0}
        for l in shift:
            s_dict[l[0]] =  s_dict[l[0]]+l[1]
        if s_dict[0]>s_dict[1]:
            s_dict[0]=s_dict[0]-s_dict[1]
            del s_dict[1]
        else:
            s_dict[1]=s_dict[1]-s_dict[0]
            del s_dict[0]
        d = s_dict.keys()[0]
        a = s_dict.values()[0]
        l=len(s)
        while a>=l:
            a=a-l
        if a==0:
            return s
        else:
            return shiftd(s,a,d)

print stringShift("abc", [[0,1],[1,2]])
        
        
