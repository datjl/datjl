using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    public Player player;


    // Use this for initialization
    void Start()
    {
        player = GameObject.FindGameObjectWithTag("Player").GetComponent<Player>();
    }
    private void OnTriggerEnter2D(Collider2D col)
    {
        if (col.CompareTag("Player")) 
        {
          player.Knockback(400f, player.transform.position);
        }

        Player.health -= 1;
    }
}