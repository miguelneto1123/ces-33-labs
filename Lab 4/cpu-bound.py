import time

start = time.time()

for i in range(0, 1000000000):
	pass

print("Elapsed time: %d" % (time.time()-start))