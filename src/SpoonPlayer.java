import java.util.Arrays;

public class SpoonPlayer {
    int card;
    int[] hand;
    boolean match;
    public void setHand(int[] hand){
        this.hand = hand;
    }
    public SpoonPlayer(boolean match){
        this.hand = new int[]{-1, -1, -1, -1};
        this.match = match;
    }
    public int play(int card){
        this.card = card;
        if(match){
            return playMatch(card);
        }
        return playConsec(card);
    }
    public int playMatch(int card){
        int most = 1;
        int index = 0;
        int count = 1;
        Arrays.sort(hand);
        int goalCard = hand[0]%13;
        for(int i = 1; i < hand.length; i++){
            if(goalCard == hand[i]%13){
                count++;
                if(count > most){
                    most = count;
                    index = i;
                }
            }
            else{
                count = 1;
                goalCard = hand[i]%13;
            }
        }
        if(most == 1){
            for(int i = 0; i < hand.length; i++){
                if(card%13 == hand[i]%13){
                    if(i == 0){
                        int temp = hand[1];
                        hand[1] = card;
                        return temp;
                    }else{
                        int temp = hand[i -1];
                        hand[i -1] = card;
                        return temp;
                    }
                }
            }
        }else if(most == 2){
            if(hand[index]%13 == card%13){
                int temp;
                if(index == 3){
                    temp = hand[0];
                    hand[0] = card;
                }else{
                    temp = hand[index + 1];
                    hand[index +1] = card;
                }
                return temp;
            }
            if(hand[(index +1)%4]%13 == hand[(index +2)%4]%13 &&
                    hand[(index+1)%4]%13 == card%13){
                index = (index + 2) %4;
                if(index == 3){
                    int temp = hand[0];
                    hand[0] = card;
                    return temp;
                }else{
                    int temp = hand[index +1];
                    hand[index +1] = card;
                    return temp;
                }
            }
            else{
                if(hand[(index+1)%4]%13 == card%13){
                    int temp = hand[(index+2)%4];
                    hand[(index+2)%4] = card;
                    return temp;
                }else if(hand[(index+2)%4]%13 == card%13){
                    int temp = hand[(index+1)%4];
                    hand[(index+1)%4] = card;
                    return temp;
                }
            }
        }else if(most == 3){
            if(hand[index]%13 == card%13){
                int temp = hand[(index + 1)%4];
                hand[(index + 1)%4] = card;
                return temp;
            }
        }
        return card;
    }
    public boolean isConsec(int c, int b){
        return ((b-1)/13 == (c-1)/13) && (c == b+1 || b == c+1 || (b%13 == 0 && c%13 == 1) || (c%13 == 0 && b%13 == 1));
    }
    public int playConsec(int card){
        int most = 1;
        int index = 0;
        int kindex = -1;
        int count = 1;
        Arrays.sort(hand);
        int goalCard = hand[0];
        for(int i = 1; i < hand.length; i++){
            if((goalCard + 1 == hand[i] && (goalCard-1)/13 == (hand[i] -1)/13)){
                count++;
                goalCard = hand[i];
                if(count > most){
                    most = count;
                    index = i;
                }
            }
            else{
                count = 1;
                goalCard = hand[i];
            }
        }
        if(hand[index]%13 == 0){
            for(int i = 0; i < 4; i++){
                if(hand[i]%13 == 1 && (hand[index]-1)/13 == (hand[i] -1)/13){
                    most++;
                    kindex = i;
                }
            }
        }else if(most == 1){
            for(int i = 0; i < 4; i++){
                if(hand[i]%13 == 0){
                    for(int j = 0; j < 4; j++){
                        if(hand[j]%13 == 1 && (hand[i]-1)/13 == (hand[j] -1)/13){
                            most = 2;
                            index = j;
                            kindex = i;
                        }
                    }
                }
            }
        }
        if(most == 1){
            for(int i = 0; i < hand.length; i++){
                if(isConsec(card, hand[i])){
                    if(i == 0){
                        int temp = hand[1];
                        hand[1] = card;
                        return temp;
                    }else{
                        int temp = hand[i -1];
                        hand[i -1] = card;
                        return temp;
                    }
                }
            }
        }else if(most == 2) {
            if (hand[index] % 13 == 1) {
                if (isConsec(hand[index], card) || (kindex != -1 && isConsec(hand[kindex], card))) {
                    if (index == 0 && hand[3] % 13 != 0) {
                        int temp = hand[3];
                        hand[3] = card;
                        return temp;
                    } else {
                        int temp = hand[(index + 1) % 4];
                        hand[(index + 1) % 4] = card;
                        return temp;
                    }
                }
            } else if(isConsec(hand[index], card) || isConsec(hand[index - 1], card)){
                int temp = hand[(index + 1) % 4];
                hand[(index + 1) % 4] = card;
                return temp;
            } else if(isConsec(hand[(index +1)%4],hand[(index +2)%4]) &&
                    (isConsec(card ,hand[(index +2)%4])|| isConsec(hand[(index +1)%4], card))){
                int temp = hand[index];
                hand[index] = card;
                return temp;
            } else if (isConsec(card ,hand[(index +2)%4])){
                int temp = hand[(index+1)%4];
                hand[(index+1)%4] = card;
                return temp;
            } else if (isConsec(card, hand[(index+1)%4])){
                int temp = hand[(index+2)%4];
                hand[(index+2)%4] = card;
                return temp;
            }
        }else if(most == 3){
            if(kindex == -1 && (isConsec(hand[index], card) || isConsec(hand[index - 2], card))){
                int temp = hand[(index + 1)%4];
                hand[(index + 1)%4] = card;
                return temp;
            }else if(kindex != -1){
                if(isConsec(hand[kindex], card)){
                    if(!isConsec(hand[kindex +1], hand[kindex]) &&
                            !isConsec(hand[kindex + 1], hand[index])){
                        int temp = hand[kindex + 1];
                        hand[kindex + 1] = card;
                        return temp;
                    }if(!isConsec(hand[(kindex + 2)%4], hand[kindex]) &&
                            !isConsec(hand[(kindex + 2)%4], hand[index])){
                        int temp = hand[(kindex + 2)%4];
                        hand[(kindex + 2)%4] = card;
                        return temp;
                    }if(!isConsec(hand[(kindex + 3)%4], hand[kindex]) &&
                            !isConsec(hand[(kindex + 3)%4], hand[index])){
                        int temp = hand[(kindex + 3)%4];
                        hand[(kindex + 3)%4] = card;
                        return temp;
                    }
                }else if(isConsec(hand[index], card)){
                    if(!isConsec(hand[(kindex + 1)%4], hand[kindex]) &&
                            !isConsec(hand[(kindex + 1)%4], hand[index])){
                        int temp = hand[(kindex + 1)%4];
                        hand[(kindex + 1)%4] = card;
                        return temp;
                    }if(!isConsec(hand[(kindex + 2)%4], hand[kindex]) &&
                            !isConsec(hand[(kindex + 2)%4], hand[index])){
                        int temp = hand[(kindex + 2)%4];
                        hand[(kindex + 2)%4] = card;
                        return temp;
                    }if(!isConsec(hand[(kindex + 3)%4], hand[kindex]) &&
                            !isConsec(hand[(kindex + 3)%4], hand[index])){
                        int temp = hand[(kindex + 3)%4];
                        hand[(kindex + 3)%4] = card;
                        return temp;
                    }
                }
            }
        }
        return card;
    }

    public boolean won(){
        if(wonByMatch(0)){
            return true;
        }
        return wonByConsecutive();
    }
    public boolean wonByMatch(int i){
        if(i > hand.length-2){
            return true;
        }
        return hand[i]%13 == hand[i +1]%13 && wonByMatch(i+1);
    }
    public String toString(){
        String s = "[" + hand[0];
        for(int i = 1; i < hand.length; i++){
            s += "," + hand[i];
        }
        return s + "]";
    }
    public boolean wonByConsecutive(){
        Arrays.sort(hand);
        int suit = (hand[0] -1)/13;
        for(int i = 1; i < hand.length; i++){
            if(suit != (hand[i] -1)/13){
                return false;
            }
        }
        return isConsecutive(0);
    }
    public boolean isConsecutive(int i){
        if(i > hand.length-2){
            return true;
        }
        return (hand[i] + 1 == hand[i +1] || (hand[i]%13 == 1 && hand[3]%13 == 0) )&& isConsecutive(i+1);
    }


}
