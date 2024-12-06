data = []
def parse():
    with open("input2") as f:
        for line in f:
            nums = list(map(int,line.split()))
            data.append(nums)

def is_safe(l):
    diffs = list(map(lambda x:x[0]-x[1], zip(l[:-1],l[1:])))
    return all(map(lambda x: x>0 and x <= 3, diffs)) or all(map(lambda x: x < 0 and x >= -3, diffs))

def part1():
    count_safe = 0
    for nums in data:
        if is_safe(nums):
            count_safe+=1
    print(count_safe)

def part2():
    count_safe = 0
    for nums in data:
        if any(map(is_safe, [nums[:i]+nums[i+1:] for i in range(len(nums))])):
            count_safe+=1
    print(count_safe)

parse()
part1()
part2()
