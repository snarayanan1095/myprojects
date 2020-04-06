#!/usr/bin/env python
// Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
def moveZeroes(nums):
    t = []
    l=len(nums)
    for i in range(0,l-1):
        if nums[i]==0:
            t.append(i)
            nums.append(nums[i])

    while t:
        nums.pop(t.pop())

    return nums

print moveZeroes([0,1,0,13,12])
