


def part1():
    

    with open("input6.txt") as f:
        line = f.read()
        for i in range(4,len(line)):
            if len(set(line[i-4:i])) == len(line[i-4:i]):
                print(i)
                return




def part2():
    

    with open("input6.txt") as f:
        line = f.read()
        for i in range(14,len(line)):
            if len(set(line[i-14:i])) == len(line[i-14:i]):
                print(i)
                return
 




part1()
part2()
