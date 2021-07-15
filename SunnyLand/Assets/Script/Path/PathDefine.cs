using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PathDefine: MonoBehaviour
{
    public Transform[] listPoints;
    public int StartAt = 0;
    public int directionMove = 1;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
    public void Ondraw ()
    {
        if (listPoints == null || listPoints.Length < 2)
            return;
        for(int i = 1;i<listPoints.Length;i++)
        {
            Gizmos.DrawLine(listPoints[i - 1].position, listPoints[i].position);
        }
    }
    public Transform getPointAt(int p)
    {
        return listPoints[p];
    }
    public Transform getNextPoint()
    {
        if (StartAt == 0)
            directionMove = 1;
        else if (StartAt == listPoints.Length - 1)
            directionMove = -1;
        StartAt += directionMove;
        return listPoints[StartAt];
    }
}
