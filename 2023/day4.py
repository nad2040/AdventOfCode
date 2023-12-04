
winnings = []
givens = []


def parse():
    with open("input4") as f:
        for card, line in enumerate(f,1):
            line = line.split(': ')[1]
            winning, given = line.split(' | ')[0], line.split(' | ')[1]
            winning = winning.strip()
            given = given.strip()
            winning = list(map(int, winning.split()))
            given = list(map(int, given.split()))
            winnings.append(set(winning))
            givens.append(given)
            # print(winning)
            # print(given)


def part1():
    sum = 0
    for i in range(len(winnings)):
        score = 0
        found_one = False
        winning = winnings[i]
        given = givens[i]
        for g in given:
            if not found_one:
                if g in winning:
                    found_one = True
                    score += 1
            else:
                if g in winning:
                    score *= 2
        sum += score
    print(sum)

def part2():
    count_tickets = [1 for _ in range(len(winnings))]
    for i in range(len(winnings)):
        count = 0
        winning = winnings[i]
        given = givens[i]
        for g in given:
            if g in winning:
                count += 1
        for j in range(i+1, i+count+1):
            count_tickets[j] += count_tickets[i]
    # print(count_tickets)
    print(sum(count_tickets))

parse()
part1()
part2()
