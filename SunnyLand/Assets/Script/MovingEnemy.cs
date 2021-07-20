using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MovingEnemy : MonoBehaviour
{
    public PathDefine thePath;
    public bool faceright = true;
    public float speed = 2f;
    private Transform _targetPoint;
    public Rigidbody2D rb;

    void Awake()
    {
        rb = GetComponent<Rigidbody2D>();
    }
    // Start is called before the first frame update
    void Start()
    {
        if (thePath == null)
            return;
        _targetPoint = thePath.getPointAt(0);
    }

    // Update is called once per frame
    private void FixedUpdate()
    {

    }

    void Update()
    {
        if (thePath == null)
            return;
        transform.position = Vector3.MoveTowards(transform.position, _targetPoint.position, speed * Time.deltaTime);

        var distanceTarget = (transform.position - _targetPoint.position).sqrMagnitude;
        
        if(distanceTarget < 0.1f)
        {
            flip();
            _targetPoint = thePath.getNextPoint();
        }
    }
 
    public void flip()
    {
        faceright = !faceright;
        Vector3 Scale;
        Scale = transform.localScale;
        Scale.x *= -1;
        transform.localScale = Scale;
    }


}
