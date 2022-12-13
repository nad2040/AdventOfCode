from functools import cmp_to_key

def compare(L,R):
    # print(f"compare {L} vs {R}")
    if isinstance(L, int) and isinstance(R, int):
        if L < R:
            # print("left side is smaller so right order")
            return 1
        elif L > R:
            # print("right side is smaller so wrong order")
            return -1
    elif isinstance(L, list) and isinstance(R, list):
        for i in range(max(len(L),len(R))):
            if i == len(L):
                # print("left side ran out of items so right order")
                return 1
            elif i == len(R):
                # print("right side ran out of items so wrong order")
                return -1
            left = L[i]
            right = R[i]
            result = compare(left,right)
            if result != None:
                return result
    else:
        # print("mixed types; ",end='')
        if isinstance(L, int):
            L = [L]
            # print(f"convert left to {L} and retry comparison")
        else:
            R = [R]
            # print(f"convert right to {R} and retry comparison")
        result = compare(L,R)
        if result != None:
            return result

def part1():
    results = []
    with open("input13.txt") as f:
        L = []
        R = []
        for i, line in enumerate(f,start=1):
            line = line.strip()
            if i%3 == 1:
                L = eval(line)
            elif i%3 == 2:
                R = eval(line)
                results.append(compare(L,R))

    # print(results)
    sum = 0
    for i,result in enumerate(results):
        # print(i,result) 
        if result == 1:
            sum += i+1

    print(sum)


def part2():
    packets = []
    with open("input13.txt") as f:
        L = []
        R = []
        for i, line in enumerate(f,start=1):
            line = line.strip()
            if i%3 == 1:
                L = eval(line)
                packets.append(L)
            elif i%3 == 2:
                R = eval(line)
                packets.append(R)

    packets.append([[2]])
    packets.append([[6]])
    packets.sort(key=cmp_to_key(compare),reverse=True)
    
    product = 1
    for i,p in enumerate(packets,start=1):
        # print(p)
        if p == [[2]] or p == [[6]]:
            product *= i
    print(product)

part1()
part2()
