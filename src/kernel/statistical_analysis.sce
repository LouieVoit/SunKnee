clear();
numberOfItems=100;
M=csvRead("data_numberOfItems_"+string(numberOfItems)+".csv");
M=M(2:$,:);
numberOfSamples=size(M,1);
minimumOrder=numberOfItems - 1;
maximumOrder=numberOfItems * (numberOfItems-1) / 2;
data=M(:,2);
f=figure();
f.background=-2;
histplot(maximumOrder-minimumOrder+1,data,style=2,normalization=%f);
xtitle("histogram of the number q of questions asked to the user ("+string(numberOfItems)+" items)","number q of questions asked to the user ("+string(minimumOrder)+"<q<"+string(maximumOrder)+")","frequency (over "+string(numberOfSamples)+" samples)");
averageOrder=sum(M(:,2))/numberOfSamples
