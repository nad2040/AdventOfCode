
def part1():
    types = ""

    with open("input3.txt") as f:
        for line in f:
            line = line.strip()
            line =[line[:len(line)//2],line[len(line)//2:]]
            # print (line)
            for c in line[0]:
                if c in line[1]:
                    # print(c)
                    types += c
                    break

    sum = 0
    for c in types:
        if ord(c)>96:
            sum += ord(c)-96
        else:
            sum += ord(c)-64+26
    
    print(sum)

def part2():
    types = ""
    lines = []
    with open("input3.txt") as f:
        for line in f:
            line = line.strip()
            lines += [line]

    for i in range(0,len(lines),3):
        for c in lines[i]:
            if c in lines[i+1] and c in lines[i+2]:
                # print(c)
                types += c
                break
    sum = 0
    for c in types:
        if ord(c)>96:
            sum += ord(c)-96
        else:
            sum += ord(c)-64+26
    print(sum) 


part1()
part2()
