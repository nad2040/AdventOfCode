
forest = []

def parse():
    global forest
    with open("input8.txt") as f:
        for line in f:
            line = line.strip()
            l = []
            for c in line:
                l += [int(c)]
            forest += [l]

def isVisible(i,j):
    
    for x,y in [(0,1),(0,-1),(1,0),(-1,0)]:
        r = i + x
        c = j + y
        while r in range(0,len(forest)) and c in range(0,len(forest[0])):
            if forest[r][c] >= forest[i][j]:
                break
            r += x
            c += y
        else:
            return True
                
    return False       

def part1():
    sum = 0
    for i in range(len(forest)):
        for j in range(len(forest[0])):
            if i == 0 or j == 0 or i == len(forest)-1 or j == len(forest[0])-1:
                sum += 1 
            elif isVisible(i,j):
                sum += 1

            
    print(sum)


def scenicScore(i,j):
    product = 1
    for x,y in [(0,1),(0,-1),(1,0),(-1,0)]:
        count = 0
        r = i + x
        c = j + y
        while r in range(0,len(forest)) and c in range(0,len(forest[0])):
            if forest[r][c] < forest[i][j]:
                count += 1 
                r += x
                c += y
            elif forest[r][c] == forest[i][j]:
                count += 1
                break
            else:
                break

        product *= count

    return product


def part2():
    coord = (0,0)
    max = 0

    for i in range(len(forest)):
        for j in range(len(forest[0])):
            if scenicScore(i,j) > max:
                max = scenicScore(i,j)
                coord = (i,j)
    print(max)
 

parse()
part1()
part2()
