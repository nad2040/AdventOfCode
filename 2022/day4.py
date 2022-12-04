


def part1():
    sum = 0

    with open("input4.txt") as f:
        
        for line in f:
            line = line.strip()
            line = line.split(",")
            r1L = int(line[0].split("-")[0])
            r1R = int(line[0].split("-")[1])
            r2L = int(line[1].split("-")[0])
            r2R = int(line[1].split("-")[1])
            if r1L <= r2L and r1R >= r2R or r2L <= r1L and r2R >= r1R:
                sum +=1 
        
    print(sum)

def part2():
    sum = 0
    with open("input4.txt") as f:
        for line in f:
            line = line.strip()
            line = line.split(",")
            r1L = int(line[0].split("-")[0])
            r1R = int(line[0].split("-")[1])
            r2L = int(line[1].split("-")[0])
            r2R = int(line[1].split("-")[1])
            if r1L <= r2R and r1R >= r2L:
                sum+=1
    print(sum) 


part1()
part2()
