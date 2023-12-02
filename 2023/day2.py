def part1():
    sum = 0
    with open("input2") as f:
        for idx, line in enumerate(f,1):
            line = line.split(": ")
            line = line[1]
            sets = line.split("; ")
            max_red = 0
            max_green = 0
            max_blue = 0
            for s in sets:
                for x in s.split(", "):
                    x = x.strip()
                    if len(x) > 0:
                        # print(x)
                        num_color = x.split(" ")
                        num = int(num_color[0])
                        color = num_color[1]
                        if color == 'red':
                            max_red = max(num, max_red)
                        if color == 'green':
                            max_green = max(num, max_green)
                        if color == 'blue':
                            max_blue = max(num, max_blue)
            # print(f"{max_red=},{max_green=},{max_blue=}")

            if max_red <= 12 and max_green <= 13 and max_blue <= 14:
                # print(idx)
                sum += idx


    print(sum)

def part2():
    sum = 0
    with open("input2") as f:
        for idx, line in enumerate(f,1):
            line = line.split(": ")
            line = line[1]
            sets = line.split("; ")
            max_red = 0
            max_green = 0
            max_blue = 0
            for s in sets:
                for x in s.split(", "):
                    x = x.strip()
                    if len(x) > 0:
                        # print(x)
                        num_color = x.split(" ")
                        num = int(num_color[0])
                        color = num_color[1]
                        if color == 'red':
                            max_red = max(num, max_red)
                        if color == 'green':
                            max_green = max(num, max_green)
                        if color == 'blue':
                            max_blue = max(num, max_blue)
            # print(f"{max_red=},{max_green=},{max_blue=}")

            sum += max_red*max_green*max_blue

    print(sum)

part1()
part2()
