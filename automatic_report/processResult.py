import sys

if __name__ == "__main__":
	file_name = sys.argv [1]
	file = open (file_name, "r")
	lines = file.readlines ()
	
	#7 number_of_user: 2, 5, 10, 20, 30, 40, 50
	#each record contain: type speed and delay
	records = [[list() for x in xrange(2)] for x in xrange(50)] 
	
	test_cases = [[0 for x in xrange(5)] for x in xrange (100)]
	counter = 0
	last_n_user = 0
	last_type_spd = 0
	last_exp_id = 0

	for line in lines:
		if ('***' in line or '---' in line):
			continue

		ls = line.split ()

		#the same
		n_user = int (ls[1])
		type_spd = int (ls[2])
		exp_id = int (ls[3])
		string = int (ls[4])

		if (last_n_user != n_user or last_type_spd != type_spd or last_exp_id != exp_id):
			test_cases = [[0 for x in xrange(5)] for x in xrange (100)]
			last_n_user = n_user
			last_type_spd = type_spd
			last_exp_id = exp_id

		#found writing record
		if (ls[0] == 'W'):
			
			w_time = int (ls[5])

			test_cases[string][0] = n_user
			test_cases[string][1] = type_spd
			test_cases[string][2] = exp_id
			test_cases[string][4] = w_time

			#add to record
			if (test_cases[string][3] != 0 and test_cases[string][4] != 0):
				_delay = test_cases[string][3] - w_time
				if (_delay > 0):
					res = list ()
					res.append (type_spd)
					res.append (_delay)
					records[n_user - 1].append (res)

		#found reading record
		if (ls[0] == 'R'):
			r_time = int (ls[5])

			test_cases[string][3] = r_time

			#add to record
			if (test_cases[string][4] != 0 and r_time != 0):
				_delay = r_time - test_cases[string][4]
				if (_delay > 0):
					res = list ()
					res.append (type_spd)
					res.append (_delay)
					records[n_user - 1].append (res)

			#if (test_cases[string][0] == n_user && test_cases[string[1] == type_spd && test_cases[string[2]] = exp_id):
			test_cases[string][3] = r_time
	#test
	#print 'TEST'
	#for i in range (len (records [2])):
	#	print records[2][i]
	file.close ()

	#write the result
	file = open (sys.argv[2], "w")
	us = range (1, 51)
	#print len (records)
	file.write ("user speed delay\n")
	for u in us:
		for i in range (len (records[u - 1])):
			if (len (records[u - 1][i]) == 2):
				file.write (str(u) + " " + str(records[u-1][i][0]) + " " + str(records[u-1][i][1]) + "\n")
	file.close
