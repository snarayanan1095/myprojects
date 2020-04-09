"""
Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.
"""

class Solution(object):
    def backspaceCompare(self, S, T):
        """
        :type S: str
        :type T: str
        :rtype: bool
        """
        f=0
        so = ''
        for i in S[::-1]:
            if i == '#':
                f+=1
            elif f>0:
                f-=1
            else: 
                so=so+i
         
        f=0
        st=''
        for j in T[::-1]:
            if j == '#':
                f+=1
            elif f>0:
                f-=1
            else: 
                st=st+j
        if so[::-1] == st[::-1]:
            return True
