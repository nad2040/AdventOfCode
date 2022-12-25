
class CircularLinkedList:
    def __init__(self,num,left=None,right=None):
        self.num,self.left,self.right=num,left,right
    
    def __str__(self):
        return f"{self.num}"

    def move_right(self,count=1):
        for _ in range(count):
            left = self.left
            right = self.right
            rightright = self.right.right
            left.right = right
            rightright.left = self
            right.left = left
            right.right = self
            self.left = right
            self.right = rightright

    def find_right(self,count):
        trav = self
        for _ in range(count):
            trav=trav.right
        return trav.num


    def print_loop(self):
        trav = self
        while trav.right != self:
            print(trav,end=" ")
            trav = trav.right
        print(trav)


encrypted=[]
nodes = []
nums = {}

def parse(part2=False):
    global encrypted,nodes,nums
    encrypted = []
    nodes = []
    nums = {}
    with open("input20.txt") as f:
        for line in f:
            line = line.strip()
            coordinate = int(line)
            if part2:
                coordinate *= 811589153
            encrypted.append(coordinate)

    for num in encrypted:
        node = CircularLinkedList(num)
        nodes.append(node)
        if num not in nums:
            nums[num] = [node]
        else:
            nums[num].append(node)
    for i,node in enumerate(nodes):
        node.left = nodes[(i-1)%len(nodes)]
        node.right = nodes[(i+1)%len(nodes)]

def mix(n=1):
    for _ in range(n):
        for num in encrypted:
            nums[num][0].move_right(num%(len(encrypted)-1))
            node = nums[num].pop(0)
            nums[num].append(node)
            # nums[0][0].print_loop()

def part1():
    print(nums[0][0].find_right(1000%len(encrypted)) + nums[0][0].find_right(2000%len(encrypted)) + nums[0][0].find_right(3000%len(encrypted)))

def part2():
    print(nums[0][0].find_right(1000%len(encrypted)) + nums[0][0].find_right(2000%len(encrypted)) + nums[0][0].find_right(3000%len(encrypted)))

parse()
mix()
part1()
parse(part2=True)
mix(10)
part2()
