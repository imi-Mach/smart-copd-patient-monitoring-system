import pandas
from sklearn import model_selection
from sklearn.tree import DecisionTreeClassifier
import pickle

try:
    dataset = pandas.read_csv("../../../../../../../resource/train.csv")
except FileNotFoundError:
    print("Training Set not Found")

# Split-out validation dataset
train_df, val_df = model_selection.train_test_split(dataset, test_size = 0.2)
train = train_df.values
val = val_df.values

model = DecisionTreeClassifier()
model.fit(train[:,0:15], train[:,15])

pickle.dump(model, open('../../../../../../../resource/model.sav', 'wb'))

 
# # later...
 
# # load the model from disk
# # loaded_model = pickle.load(open(filename, 'rb'))
# # result = loaded_model.score(X_test, Y_test)
# # print(result)
