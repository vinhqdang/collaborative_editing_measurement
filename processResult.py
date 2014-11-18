import sys
import matplotlib.pyplot as plt

if __name__ == "__main__":
	file_name = sys.argv [1]
	file = open (file_name, "r")
	lines = file.readlines ()
	
	#7 number_of_user: 2, 5, 10, 20, 30, 40, 50
	#each record contain: type speed and delay
	records = [[list() for x in xrange(2)] for x in xrange(50)] 
	
	test_cases = [[0 for x in xrange(5)] for x in xrange (100)]
	counter = 0
	for line in lines:
		if (counter >= 5 and ('***' in line or '---' in line)):
			test_cases = [[0 for x in xrange(5)] for x in xrange (100)]
			counter = 0

		ls = line.split ()

		#found writing record
		if (ls[0] == 'W'):
			n_user = int (ls[1])
			type_spd = int (ls[2])
			exp_id = int (ls[3])
			string = int (ls[4])
			w_time = int (ls[5])

			test_cases[string][0] = n_user
			test_cases[string][1] = type_spd
			test_cases[string][2] = exp_id
			test_cases[string][4] = w_time

			#add to record
			if (test_cases[string][3] != 0):
				res = list ()
				res.append (type_spd)
				res.append (test_cases[string][3] - w_time)
				records[n_user - 1].append (res)
				counter += 1
		#found reading record
		if (ls[0] == 'R'):
			n_user = int (ls[1])
			type_spd = int (ls[2])
			exp_id = int (ls[3])
			string = int (ls[4])
			r_time = int (ls[5])

			#add to record
			if (test_cases[string][4] != 0):
				res = list ()
				res.append (type_spd)
				res.append (r_time - test_cases[string][4])
				records[n_user - 1].append (res)
				counter += 1

			#if (test_cases[string][0] == n_user && test_cases[string[1] == type_spd && test_cases[string[2]] = exp_id):
			test_cases[string][3] = r_time
	#test
	#print 'TEST'
	#for i in range (len (records [2])):
	#	print records[2][i]
	file.close ()

	#write the result
	file = open ("getResult.txt", "w")
	us = [1, 2, 5, 10, 20, 30, 40, 50]
	#print len (records)
	file.write ("user speed delay\n")
	for u in us:
		for i in range (len (records[u - 1])):
			if (len (records[u - 1][i]) == 2):
				file.write (str(u) + " " + str(records[u-1][i][0]) + " " + str(records[u-1][i][1]) + "\n")
	file.close
