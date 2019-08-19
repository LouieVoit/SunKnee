clear();
numberOfSamples=100000;
numberOfItems=99;
M=csvRead("data_numberOfItems_"+string(numberOfItems)+".csv");
M=M(2:$,:);
minimumOrder=numberOfItems - 1;
maximumOrder=numberOfItems * (numberOfItems-1) / 2;
data=M(:,2);
figure();
histplot(maximumOrder-minimumOrder+1,data,style=2,normalization=%f);
xtitle("histogram of the number q of questions asked to the user ("+string(numberOfItems)+" items)","number q of questions asked to the user ("+string(minimumOrder)+"<q<"+string(maximumOrder)+")","frequency (over "+string(numberOfSamples)+" samples)");