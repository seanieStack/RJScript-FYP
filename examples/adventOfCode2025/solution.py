#part1
with open("input") as f:
    nums = [int(y[1:]) if y[0] == "R" else -int(y[1:]) for y in (x.strip() for x in f)]

cur = 50
count = 0
for num in nums:
    cur += num
    cur = cur % 100

    if cur == 0:
        count += 1

print(count)

#part2
with open("input") as f:
    nums = [int(y[1:]) if y[0] == "R" else -int(y[1:]) for y in (x.strip() for x in f)]

cur = 50
count = 0
for num in nums:
    start = cur
    end = cur + num

    if num > 0:
        pass_zero = end // 100 - start // 100
    else:
        pass_zero = start // 100 - end // 100

    count += pass_zero
    cur = end % 100

print(count)