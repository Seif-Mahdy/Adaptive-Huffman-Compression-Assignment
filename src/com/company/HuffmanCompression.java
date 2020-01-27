package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class HuffmanCompression {
    public HufmmanNode rootNode = new HufmmanNode(5);
    public HufmmanNode currentNYTNode = new HufmmanNode();
    public HufmmanNode currentNode = new HufmmanNode();
    public boolean isLeft = false;
    public ArrayList<String> shortCodeTable = new ArrayList<>();
    public boolean check = false;


    public void fillTable(ArrayList<String> table) {
        table.add("00");
        table.add("01");
        table.add("10");
        table.add("11");

    }

    public StringBuilder comression(String message) {
        fillTable(shortCodeTable);
        StringBuilder compressedMessage = new StringBuilder();
        ShortCode obj = new ShortCode('A', "00");

        for (int i = 0; i < message.length(); i++) {
            currentNode = searchTree(String.valueOf(message.charAt(i)), rootNode);
            if (currentNode == null) {
                if (i == 0) {
                    //   System.out.println(shortCodeTable.get(((int)message.charAt(i))-65));
                    compressedMessage.append(shortCodeTable.get(((int) message.charAt(i)) - 65));
                    updateTree(message.charAt(i));
                } else {
                    // System.out.println("NYT  "+currentNYTNode.nodeCode);
                    compressedMessage.append(currentNYTNode.nodeCode);
                    // System.out.println(shortCodeTable.get(((int)message.charAt(i))-65));
                    compressedMessage.append(shortCodeTable.get(((int) message.charAt(i)) - 65));
                    updateTree(message.charAt(i));
                }
            } else {
                //   System.out.println("curNode  "+currentNode.nodeCode);
                compressedMessage.append(currentNode.nodeCode);
                updateTree(message.charAt(i));
            }
        }
        return compressedMessage;

    }

    public void updateTree(char symbol) {
        HufmmanNode newNytNode = new HufmmanNode();
        HufmmanNode newSymbolNode = new HufmmanNode();
        if (currentNode == null) {


            if (rootNode.leftNode == null && rootNode.rightNode == null) {

                newNytNode.nodeNumber = rootNode.nodeNumber - 2;
                newNytNode.nodeCode = "0";
                newNytNode.symbolCount = 0;
                newNytNode.previous = rootNode;
                newNytNode.leftNode = null;
                newNytNode.rightNode = null;
                newNytNode.nodeSymbol = '#';
                rootNode.leftNode = newNytNode;
                newSymbolNode.nodeSymbol = symbol;
                newSymbolNode.nodeNumber = rootNode.nodeNumber - 1;
                newSymbolNode.leftNode = null;
                newSymbolNode.rightNode = null;
                newSymbolNode.nodeCode = "1";
                newSymbolNode.symbolCount = 1;
                newSymbolNode.previous = rootNode;
                rootNode.rightNode = newSymbolNode;
                rootNode.symbolCount = 1;
                currentNYTNode = newNytNode;
                currentNode = currentNYTNode;


            } else {

                newNytNode.nodeNumber = currentNYTNode.nodeNumber - 2;
                newNytNode.nodeCode = currentNYTNode.nodeCode + '0';
                newNytNode.symbolCount = 0;
                newNytNode.previous = currentNYTNode;
                newNytNode.nodeSymbol = '#';
                currentNYTNode.leftNode = newNytNode;
                newSymbolNode.nodeSymbol = symbol;
                newSymbolNode.symbolCount = 1;
                newSymbolNode.nodeNumber = currentNYTNode.nodeNumber - 1;
                newSymbolNode.nodeCode = currentNYTNode.nodeCode + '1';
                newSymbolNode.previous = currentNYTNode;
                currentNYTNode.rightNode = newSymbolNode;
                currentNode = currentNYTNode;
                currentNYTNode = newNytNode;
                if (!isItRoot(currentNode)) {
                    swap(currentNode);
                }


            }

        } else {
            ///CORRECT  System.out.println("enter");
            swap(currentNode);
        }


    }

    public boolean isItRoot(HufmmanNode node) {
        return node.previous == null;

    }

    public void swap(HufmmanNode current) {
        ArrayList<HufmmanNode> objList = new ArrayList<>();
        if (current.previous == null) {
            rootNode.symbolCount++;
            return;
        }
        if (swapCondition(current, objList)) {
            ///CORRECT BUT IN THE SECOND SWAP SEARCH IT RETURNS B INSTEAD OF A
            ///Checked
           // System.out.println("Current node  "+current.nodeSymbol);
            //System.out.println("Sizee "+ objList.size());
            /*for (int i=0;i<objList.size();i++)
            {
                System.out.println(objList.get(0).nodeSymbol);
            }*/
            swapTwoNodes(current, objList.get(objList.size() - 1));
            reconstructeTree(rootNode);
        }


        current.symbolCount++;
        swap(current.previous);

    }

    public boolean swapCondition(HufmmanNode node, ArrayList<HufmmanNode> objList) {
        HufmmanNode tempNode = new HufmmanNode();
        tempNode = node.previous;
        //     System.out.println(node.previous.leftNode.nodeSymbol+" and "+node.previous.rightNode.nodeSymbol);
        //  System.out.println(node.nodeSymbol+" previous   "+node.previous.nodeSymbol);

        if (node.previous.leftNode == node) {

            isLeft = true;
        } else if (node.previous.rightNode == node) {

            isLeft = false;
        }

        while (true) {
            if (tempNode.previous == null && !isLeft) {
                if (node.nodeNumber < tempNode.leftNode.nodeNumber && node.symbolCount >= tempNode.leftNode.symbolCount) {
                    objList.add(tempNode.leftNode);
                }
                break;
            } else if (tempNode.previous == null) {
                if (node.nodeNumber < tempNode.rightNode.nodeNumber && node.symbolCount >= tempNode.rightNode.symbolCount) {
                    objList.add(tempNode.rightNode);
                }
                break;

            }
            if (tempNode.rightNode.symbolCount <= node.symbolCount && tempNode.rightNode.nodeNumber > node.nodeNumber) {
                objList.add(tempNode.rightNode);
            }
            tempNode = tempNode.previous;

        }

        objList.sort(new Comparator<HufmmanNode>() {
            @Override
            public int compare(HufmmanNode o1, HufmmanNode o2) {
                return o1.nodeNumber - o2.nodeNumber;
            }
        });

        return objList.size() > 0;


    }

    public void swapTwoNodes(HufmmanNode first, HufmmanNode second) {
        HufmmanNode temp, temp2;
        int nodeNumber = 0;
        temp = first;
        nodeNumber = first.nodeNumber;
        first.nodeNumber = second.nodeNumber;
        second.nodeNumber = nodeNumber;

        if (isLeft) {
            if (second.previous.leftNode == second) {
                second.previous.leftNode = temp;
                first.previous.leftNode = second;
                temp2 = first.previous;
                first.previous = second.previous;

                second.previous = temp2;
            } else if (second.previous.rightNode == second) {
                second.previous.rightNode = temp;
                first.previous.leftNode = second;
                temp2 = first.previous;
                first.previous = second.previous;
                second.previous = temp2;
            }
        } else {

            if (second.previous.leftNode == second) {
                second.previous.leftNode = temp;
                first.previous.rightNode = second;
                temp2 = first.previous;
                first.previous = second.previous;
                second.previous = temp2;
            } else if (second.previous.rightNode == second) {
                second.previous.rightNode = temp;
                first.previous.rightNode = second;
                temp2 = first.previous;
                first.previous = second.previous;
                second.previous = temp2;
            }
        }


    }


    public HufmmanNode searchTree(String symbol, HufmmanNode root) {
        HufmmanNode leftNode;
        HufmmanNode rightNode;
        if (root == null) {
            return null;
        }

        if (root.leftNode != null && root.leftNode.nodeSymbol == (symbol.charAt(0))) {
            return root.leftNode;
        } else if (root.rightNode != null && root.rightNode.nodeSymbol == symbol.charAt(0)) {
            return root.rightNode;
        }

        leftNode = searchTree(symbol, root.leftNode);
        rightNode = searchTree(symbol, root.rightNode);
        if (leftNode != null) {
            return leftNode;
        } else {
            return rightNode;
        }

    }

    public HufmmanNode searchTreeWithNodeCode(String nodeCode, HufmmanNode root) {
        HufmmanNode leftNode;
        HufmmanNode rightNode;
        if (root == null) {

            return null;
        }
        if (root.leftNode != null && root.leftNode.nodeCode.equals(nodeCode)) {

            return root.leftNode;
        } else if (root.rightNode != null && root.rightNode.nodeCode.equals(nodeCode)) {
            return root.rightNode;
        }

        leftNode = searchTreeWithNodeCode(nodeCode, root.leftNode);
        rightNode = searchTreeWithNodeCode(nodeCode, root.rightNode);
        if (leftNode != null) {
            return leftNode;
        }

        return rightNode;
    }

    public StringBuilder decompression(StringBuilder compressedMesaage) {
        fillTable(shortCodeTable);
        rootNode = new HufmmanNode(0);
        currentNode = new HufmmanNode();
        currentNYTNode = new HufmmanNode();
        boolean check=false;

        StringBuilder decompressedMessage = new StringBuilder();
        String tempCode = "";
        char symbol = (char) (shortCodeTable.indexOf(compressedMesaage.charAt(0) + "" + compressedMesaage.charAt(1)) + 65);;
        decompressedMessage.append(symbol);
        currentNode = searchTreeWithNodeCode(String.valueOf(symbol), rootNode);
        updateTree(symbol);
     //   System.out.println("nytBef "+currentNYTNode.nodeCode);


        for (int i = 2; i < compressedMesaage.length(); ) {

            tempCode += compressedMesaage.charAt(i);
           // System.out.println("tempCode "+tempCode);
            currentNode= searchTreeWithNodeCode(tempCode,rootNode);
            if (tempCode.equals(currentNYTNode.nodeCode)) {
                i++;
                symbol = (char) (shortCodeTable.indexOf(compressedMesaage.charAt(i) + "" + compressedMesaage.charAt(i + 1)) + 65);
                decompressedMessage.append(symbol);
                currentNode=searchTree(String.valueOf(symbol),rootNode);
                updateTree(symbol);
                i+=2;
                tempCode ="";
            }
            else {
                if ( currentNode != null && currentNode.nodeSymbol != '#') {
                    decompressedMessage.append(currentNode.nodeSymbol);
                    symbol=currentNode.nodeSymbol;
                    //currentNode=searchTree(String.valueOf(symbol),rootNode);
                    updateTree(symbol);
                    i++;
                    tempCode = "";

                }
                else
                {
                    i++;
                }
            }

        }

        return decompressedMessage;

    }

    public void reconstructeTree(HufmmanNode root) {

        if (root == null) {
            return;
        }
        if (root.leftNode != null) {
            // root.leftNode.nodeNumber = root.nodeNumber - 2;
            root.leftNode.nodeCode = root.nodeCode + '0';
        }
        if (root.rightNode != null) {
            //  root.rightNode.nodeNumber = root.nodeNumber - 1;
            root.rightNode.nodeCode = root.nodeCode + '1';
        }
        reconstructeTree(root.leftNode);
        reconstructeTree(root.rightNode);

    }

    public void printTree(HufmmanNode root) {
        if (root == null) {
            return;
        }
        System.out.println("symbol" + " " + root.nodeSymbol);
        System.out.println("nodeCount " + root.symbolCount);
        System.out.println("nodeNumber  " + root.nodeNumber);
        System.out.println("nodeCode  " + root.nodeCode);
        printTree(root.leftNode);
        printTree(root.rightNode);


    }

    public void printTree_2() {
        printTree(rootNode);
    }

}
