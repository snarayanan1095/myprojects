#!/usr/bin/env python

"""
Given an array of strings, group anagrams together
"""

def groupAnagrams(strs):
    op_dict = dict()
    for s in strs:
        ss = str("".join(sorted(s)))
        if ss in op_dict.keys():
            op_dict[ss].append(str(s))
        else:
            op_dict[ss] = [str(s)]

    return op_dict.values()
    
print groupAnagrams(["eat", "tea", "tan", "ate", "nat", "bat"])     
