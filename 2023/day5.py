seeds = []
maplist = []

def mapper(inputs):
    def part1mapper(x):
        for (dst_start, src_start, length) in inputs:
            if x in range(src_start, src_start+length):
                return dst_start + x - src_start
        return x
    def part2mapper(seed_ranges):
        new_ranges = []
        while seed_ranges:
            (seed_start, seed_end) = seed_ranges.pop(0)
            is_id = True
            for (dst_start, src_start, length) in inputs:
                src_end = src_start+length-1
                if src_start > seed_end or seed_start > src_end:
                    continue
                else:
                    is_id = False
                    low,high = max(seed_start,src_start), min(seed_end,src_end)
                    if low == seed_start and high == seed_end:
                        new_ranges.append((low + dst_start - src_start, high + dst_start - src_start))
                    elif low == src_start and high == seed_end:
                        seed_ranges.append((seed_start,src_start-1))
                        new_ranges.append((low + dst_start - src_start, high + dst_start - src_start))
                    elif low == seed_start and high == src_end:
                        seed_ranges.append((src_end+1,seed_end))
                        new_ranges.append((low + dst_start - src_start, high + dst_start - src_start))
                    else:
                        seed_ranges.append((seed_start,src_start-1))
                        seed_ranges.append((src_end+1,seed_end))
                        new_ranges.append((low + dst_start - src_start, high + dst_start - src_start))
            if is_id:
                new_ranges.append((seed_start,seed_end))
        return new_ranges
    return part1mapper, part2mapper

def parse():
    global seeds
    with open("input5") as f:
        filestr = f.read()
        maps = filestr.split('\n\n')
        # print(maps)
        seeds = list(map(int, maps[0].split()[1:]))
        # print(seeds)
        soil = [tuple(map(int, x.strip().split())) for x in maps[1].split('\n')[1:]]
        fert = [tuple(map(int, x.strip().split())) for x in maps[2].split('\n')[1:]]
        water = [tuple(map(int, x.strip().split())) for x in maps[3].split('\n')[1:]]
        light = [tuple(map(int, x.strip().split())) for x in maps[4].split('\n')[1:]]
        temp = [tuple(map(int, x.strip().split())) for x in maps[5].split('\n')[1:]]
        humid = [tuple(map(int, x.strip().split())) for x in maps[6].split('\n')[1:]]
        loc = [tuple(map(int, x.strip().split())) for x in maps[7].split('\n')[1:-1]]

        for mat in [soil, fert, water, light, temp, humid, loc]:
            maplist.append(mapper(mat))
        # print(maplist)


def part1():
    min_loc = float("inf")
    for seed in seeds:
        loc = seed
        for mapper1,_ in maplist:
            loc = mapper1(loc)
            # print("mapped to",loc)
        min_loc = min(loc, min_loc)
    print(min_loc)


from itertools import batched

def part2():
    min_loc = float("inf")
    seed_ranges = list(map(lambda x: (x[0], x[0] + x[1] - 1), batched(seeds,2)))
    # print(seed_ranges)
    for _,mapper2 in maplist:
         seed_ranges = mapper2(seed_ranges)
         # print("mapped to",seed_ranges)
    for (a,_) in seed_ranges:
        min_loc = min(a,min_loc)
    print(min_loc)

parse()
part1()
part2()
