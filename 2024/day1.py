def part1():
    sum = 0
    list1 = []
    list2 = []
    with open("input1") as f:
        for line in f:
            nums = line.split()
            num1,num2 = int(nums[0]),int(nums[1])
            list1.append(num1)
            list2.append(num2)
    list1.sort()
    list2.sort()
    for (a,b) in zip(list1,list2):
        sum += abs(a-b)
    print(sum)

from collections import Counter

def part2():
    sum = 0
    list1 = []
    list2 = []
    with open("input1") as f:
        for line in f:
            nums = line.split()
            num1,num2 = int(nums[0]),int(nums[1])
            list1.append(num1)
            list2.append(num2)

    counts = Counter(list2)
    for n in list1:
        sum += n * counts[n]
    print(sum)


part1()
part2()
