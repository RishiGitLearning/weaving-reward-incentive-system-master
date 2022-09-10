package com.technoride.RewardIncentiveSystem.utility;

import com.technoride.RewardIncentiveSystem.entity.WeaverProductionTargets;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RewardSlab {

    public List<Map<String, List<List<Integer>>>> FindABRewardSlab(List<WeaverProductionTargets> weaverProductionTargets)
    {
        List<Map<String, List<List<Integer>>>> weaverProdTargetMapList = new ArrayList<>();
        Map<String, List<List<Integer>>> weaverProdTargetMapAB = new HashMap<>();
        Map<String, List<List<Integer>>> weaverProdTargetMapC = new HashMap<>();
        for (WeaverProductionTargets w:weaverProductionTargets) {
            String k = w.getLoom()+"_"+w.getWeftDetails();
            List<List<Integer>> slabRewardListAB = new ArrayList<>();
            List<List<Integer>> slabRewardListC = new ArrayList<>();
            List<Integer> slabAB = new ArrayList<>();
            if(!w.getShiftABPicksForRewards().equals("null"))
            {
                if(w.getShiftABPicksForRewards().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards()));
                }
            }
            if(!w.getShiftABPicksForRewards2().equals("null"))
            {
                if(w.getShiftABPicksForRewards2().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards2()));
                }
            } if(!w.getShiftABPicksForRewards3().equals("null"))
            {
                if(w.getShiftABPicksForRewards3().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards3()));
                }
            } if(w.getShiftABPicksForRewards4() !=null)
            {
                if(w.getShiftABPicksForRewards4().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards4()));
                }
            }
            if(w.getShiftABPicksForRewards5() !=null)
            {
                if(w.getShiftABPicksForRewards5().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards5()));
                }
            }
            if(w.getShiftABPicksForRewards6() !=null)
            {
                if(w.getShiftABPicksForRewards6().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards6()));
                }
            }
            if(w.getShiftABPicksForRewards7() !=null)
            {
                if(w.getShiftABPicksForRewards7().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards7()));
                }
            }
            if(w.getShiftABPicksForRewards8() !=null)
            {
                if(w.getShiftABPicksForRewards8().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards8()));
                }
            }
            if(w.getShiftABPicksForRewards9() !=null)
            {
                if(w.getShiftABPicksForRewards9().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards9()));
                }
            }
            if(w.getShiftABPicksForRewards10() !=null)
            {
                if(w.getShiftABPicksForRewards10().length()>0)
                {
                    slabAB.add(Integer.valueOf(w.getShiftABPicksForRewards10()));
                }
            }
            List<Integer> rewardAB = new ArrayList<>();
            if(w.getReward1() !=null) {
                if (w.getReward1().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward1()));
            }
            if(w.getReward2() !=null) {
                if (w.getReward2().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward2()));
            }
            if(w.getReward3() !=null) {
                if (w.getReward3().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward3()));
            }
            if(w.getReward4() !=null) {
                if (w.getReward4().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward4()));
            }
            if(w.getReward5() !=null) {
                if (w.getReward5().length() > 0) {
                    if(!w.getReward5().isEmpty()) {
                        rewardAB.add(Integer.valueOf(w.getReward5()));
                    }
                }
            }
            if(w.getReward6() !=null) {
                if (w.getReward6().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward6()));
            }
            if(w.getReward7() !=null) {
                if (w.getReward7().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward7()));
            }
            if(w.getReward8() !=null) {
                if (w.getReward8().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward8()));
            }
            if(w.getReward9() !=null) {
                if (w.getReward9().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward9()));
            }
            if(w.getReward10() !=null) {
                if (w.getReward10().length() > 0)
                    rewardAB.add(Integer.valueOf(w.getReward10()));
            }
            slabRewardListAB.add(slabAB);
            slabRewardListAB.add(rewardAB);

            List<Integer> slabC = new ArrayList<>();
            if(!w.getShiftCPicksForRewards().equals("null"))
            {
                if(w.getShiftCPicksForRewards().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards()));
                }
            }
            if(!w.getShiftCPicksForRewards2().equals("null"))
            {
                if(w.getShiftCPicksForRewards2().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards2()));
                }
            }
            if(!w.getShiftCPicksForRewards3().equals("null"))
            {
                if(w.getShiftCPicksForRewards3().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards3()));
                }
            }
            if(w.getShiftABPicksForRewards4() !=null)
            {
                if(w.getShiftCPicksForRewards4().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards4()));
                }
            }
            if(w.getShiftABPicksForRewards5() !=null)
            {
                if(w.getShiftCPicksForRewards5().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards5()));
                }
            }
            if(w.getShiftABPicksForRewards6() !=null)
            {
                if(w.getShiftCPicksForRewards6().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards6()));
                }
            }
            if(w.getShiftABPicksForRewards7() !=null)
            {
                if(w.getShiftCPicksForRewards7().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards7()));
                }
            }
            if(w.getShiftABPicksForRewards8() !=null)
            {
                if(w.getShiftCPicksForRewards8().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards8()));
                }
            }
            if(w.getShiftABPicksForRewards9() !=null)
            {
                if(w.getShiftCPicksForRewards9().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards9()));
                }
            }
            if(w.getShiftABPicksForRewards10() !=null)
            {
                if(w.getShiftCPicksForRewards10().length()>0)
                {
                    slabC.add(Integer.valueOf(w.getShiftCPicksForRewards10()));
                }
            }
            List<Integer> rewardC = new ArrayList<>();
            if(w.getReward1()!=null){
                if(w.getReward1().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward1()));
            }
            if(w.getReward2()!=null){
                if(w.getReward2().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward2()));
            }
            if(w.getReward3()!=null){
                if(w.getReward3().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward3()));
            }
            if(w.getReward4()!=null){
                if(w.getReward4().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward4()));
            }
            if(w.getReward5()!=null){
                if(w.getReward5().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward5()));
            }
            if(w.getReward6()!=null){
                if(w.getReward6().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward6()));
            }
            if(w.getReward7()!=null){
                if(w.getReward7().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward7()));
            }
            if(w.getReward8()!=null){
                if(w.getReward8().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward8()));
            }
            if(w.getReward9()!=null){
                if(w.getReward9().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward9()));
            }
            if(w.getReward10()!=null){
                if(w.getReward10().length()>0)
                    rewardC.add(Integer.valueOf(w.getReward10()));
            }

            slabRewardListC.add(slabC);
            slabRewardListC.add(rewardC);

            weaverProdTargetMapAB.put(k, slabRewardListAB);
            weaverProdTargetMapC.put(k, slabRewardListC);
        }
        for(Map.Entry<String,List<List<Integer>>> entry: weaverProdTargetMapAB.entrySet()) {
            System.out.println("Hashmap_AB" + entry.getKey() + "::" + entry.getValue());
        }

        for(Map.Entry<String,List<List<Integer>>> entry: weaverProdTargetMapC.entrySet()) {
            System.out.println("Hashmap_C" + entry.getKey() + "::" + entry.getValue());
        }
        weaverProdTargetMapList.add(weaverProdTargetMapAB);
        weaverProdTargetMapList.add(weaverProdTargetMapC);
        return weaverProdTargetMapList;
    }

}
