class Solution(object):
    def middleNode(self, head):
        """
        :type head: ListNode
        :rtype: ListNode
        """
        start = head
        c = 0
        if head==None:
            return 
        while head!=None:
            c+=1
            head = head.next
        
        mid = c/2

        for i in range(0,mid+1):
            op = start
            start = start.next
        return op
        
