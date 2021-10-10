#!/usr/bin/env python

import sys
from numpy import mat, mean

def read_input(mapper_output):
	for line in mapper_output:
		yield line.rstrip()

inp = read_input(sys.stdin)

mapper_output = [line.split('\t') for line in inp]

cum_mean = 0
cum_var = 0 
cum_n = 0

for i in mapper_output:
	cur_n = float(i[0])
	cur_mean = float(i[1])
	cur_var = float(i[2])

	cum_mean = (cum_n * cum_mean + cur_n * cur_mean) / (cur_n + cum_n)
	cum_var = (cum_n * cum_var + cur_n * cur_var) / (cur_n + cum_n) + cur_n * cum_n * (cur_mean - cum_mean) ** 2 / (cum_n + cur_n)

	cum_n += cur_n

print("%d\t%f\t%f" % (cum_n, cum_mean, cum_var))
