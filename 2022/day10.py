


def part1():
    current_x = 1
    x = [1]
    with open("input10.txt") as f:
        for line in f:
            line = line.strip()
            if line == "noop":
                x += [current_x]
            else:
                x += [current_x]
                x += [current_x]
                current_x += int(line.split()[1])
    
    sum = 0
    for i in range(20,len(x),40):
        sum += i * x[i]
    print(sum)




def part2():
    current_x = 1
    x = [1]
    with open("input10.txt") as f:
        for line in f:
            line = line.strip()
            if line == "noop":
                x += [current_x]
            else:
                x += [current_x]
                x += [current_x]
                current_x += int(line.split()[1])
    
    for i in range(1,241):
        if i%40 in range(x[i],x[i]+3):
            print('#',end="")
        else:
            print(' ',end="")
        if i%40 == 0:
            print()
        

part1()
part2()
