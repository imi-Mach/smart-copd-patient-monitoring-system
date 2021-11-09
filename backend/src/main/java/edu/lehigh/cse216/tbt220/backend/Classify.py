import pickle
import sys
import numpy as np
from io import StringIO

try:
    model = pickle.load(open('../../../../../../../resource/model.sav', 'rb'))
except FileNotFoundError:
    print("Model not Found")

data = StringIO(sys.argv[1])
dp = np.loadtxt(data, delimiter=",")
dp = dp.reshape(1, -1)

prediction = model.predict(dp)
print(int(prediction[0]))
