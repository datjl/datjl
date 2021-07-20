using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Enemy : MonoBehaviour
{
    private Animator anim;
    private Collider2D coll;
    private Rigidbody2D rb;

    public Player player;
    // Use this for initialization
    void Start()
    {
        anim = GetComponent<Animator>();
        coll = GetComponent<Collider2D>();
        player = GameObject.FindGameObjectWithTag("Player").GetComponent<Player>();

    }
    public void OnTriggerEnter2D(Collider2D coll)
    {
            Player.health -= 1;     
    }

    public void Jumpon()
    {
        anim.SetTrigger("Death");
    }

    private void Death()
    {
        Destroy(this.gameObject);
    }
}