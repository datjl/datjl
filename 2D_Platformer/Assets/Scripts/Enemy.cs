using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    [SerializeField] float enemyMovementSpeed = 1f;
    Rigidbody2D enemyRigidBody;

    // Start is called before the first frame update
    void Start()
    {
        enemyRigidBody = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        MoveEnemy();
    }

    // Move the enemy and according to its direction
    private void MoveEnemy()
    {
        if (IsFacingRight())
        {
            enemyRigidBody.velocity = new Vector2(enemyMovementSpeed, 0f);
        }
        else
        {
            enemyRigidBody.velocity = new Vector2(-enemyMovementSpeed, 0f);
        }
    }

    // Check if enemy is facing right direction
    bool IsFacingRight()
    {
        return transform.localScale.x > 0;
    }

    // Check if enemy has come to the edge of ground collider
    private void OnTriggerExit2D(Collider2D collision)
    {
        // Get the current sign and revert it. Flip enemy to face opposite direction
        transform.localScale = new Vector2(-(Mathf.Sign(enemyRigidBody.velocity.x)), 1f);
    }
}
