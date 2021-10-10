#!/usr/bin/env python

import sys
from numpy import mat, mean, var

def read_file(file_path):
	for line in file_path:
		yield line.rstrip()

inp = [float(line) for line in read_file(sys.stdin)]

num_inp = len(inp)
inp = mat(inp)
sq_inp = var(inp)

print('%d\t%f\t%f' % (num_inp, mean(inp), sq_inp))
