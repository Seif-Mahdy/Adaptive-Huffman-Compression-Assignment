package com.company;

public class HufmmanNode {
    char nodeSymbol;
    String nodeCode;
    int nodeNumber;
    int symbolCount;
    HufmmanNode leftNode;
    HufmmanNode rightNode;
    HufmmanNode previous;

    public HufmmanNode (int x) {
    nodeNumber=100;
    symbolCount=0;
    leftNode=null;
    rightNode=null;
    previous=null;
    nodeSymbol='@';
    nodeCode="";
    }

    public HufmmanNode () {

    }
}
