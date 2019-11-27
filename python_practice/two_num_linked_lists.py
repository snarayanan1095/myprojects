# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def addTwoNumbers(self, l1, l2):
        """
        :type l1: ListNode
        :type l2: ListNode
        :rtype: ListNode
        """
        op_list = ListNode(0)
        op_list_tail = op_list
        co = 0
        while l1 and l2:
            op = l1.val+l2.val+co
            if op/10>=1:
                co = 1
                op = op%10
            else:
                co=0
            l1 = l1.next
            l2=l2.next
            op_list_tail.next = ListNode(op)
            op_list_tail = op_list_tail.next
            
        return op_list.next
        
