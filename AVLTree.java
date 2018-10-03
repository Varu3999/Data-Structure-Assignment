import java.util.*;
 
class AVLNode
{    
    public AVLNode left, right;
    public Position data;
    public int height;
    public int value;

    public AVLNode(Position pos)
    {
        left = null;
        right = null;
        data = pos;
        value=pos.phraseIndex;
        height = 0;
    }     
}
 
public class AVLTree
{
    public AVLNode root;     
    Vector<Integer> inorder_val = new Vector<Integer>();
    Boolean flag = false;

    public AVLTree()
    {
        root = null;
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    public int height(AVLNode t )
    {
       if(t==null) return (-1);
       else return t.height;
    }

    public int max(int lhs, int rhs)
    {
       if(lhs>rhs) return lhs;
       else return rhs;
    }

    public void insert(Position pos)
    {
        root = insert(pos, root);
    }

    private AVLNode insert(Position pos, AVLNode t)
    {
        if (t == null)
            t = new AVLNode(pos);
        else if (pos.phraseIndex < t.value)
        {
            t.left = insert( pos, t.left );
            if( height( t.left ) - height( t.right ) == 2 )
                if( pos.phraseIndex < t.left.value )
                    t = rotateWithLeftChild( t );
                else
                    t = doubleWithLeftChild( t );
        }
        else if( pos.phraseIndex > t.value )
        {
            t.right = insert( pos, t.right );
            if( height( t.right ) - height( t.left ) == 2 )
                if( pos.phraseIndex > t.right.value)
                    t = rotateWithRightChild( t );
                else
                    t = doubleWithRightChild( t );
        }
        else;  
        t.height = max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }

    private AVLNode rotateWithLeftChild(AVLNode k2)
    {
        AVLNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = max( height( k1.left ), k2.height ) + 1;
        return k1;
    }
 
    private AVLNode rotateWithRightChild(AVLNode k1)
    {
        AVLNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    private AVLNode doubleWithLeftChild(AVLNode k3)
    {
         k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    private AVLNode doubleWithRightChild(AVLNode k1)
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }    

    public int countNodes()
    {
        return countNodes(root);
    }

    public int countNodes(AVLNode r)
    {
        if (r == null)
            return 0;
        else
        {
            int l = 1;
            l += countNodes(r.left);
            l += countNodes(r.right);
            return l;
        }
    }

    public boolean search(int val)
    {
        return search(root, val);
    }

    public boolean search(AVLNode r, int val)
    {
        boolean found = false;
        while ((r != null) && !found)
        {
            int rval = r.value;
            if (val < rval)
                r = r.left;
            else if (val > rval)
                r = r.right;
            else
            {
                found = true;
                break;
            }
            found = search(r, val);
        }
        return found;
    }

    public Vector<Integer> inorder()
    {
       if(flag==false)
        {
            inorder(root);
            flag=true;
            return inorder_val;
        }
        else
        {
            return inorder_val;
        }
    }

    public void inorder(AVLNode r)
    {
        if (r != null)
        {
            inorder(r.left);
            inorder_val.add(r.value);
            inorder(r.right);
        }
    }
}