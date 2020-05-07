package com.example.smiledetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.Frame;
import com.otaliastudios.cameraview.FrameProcessor;
import com.theartofdev.edmodo.cropper.CropImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FrameProcessor {
    private Facing camerafacing=Facing.FRONT;
    private ImageView imageView;
    private CameraView facedetectioncameraview;
    private RecyclerView bottomsheetRecyclerview;
    private BottomSheetBehavior bottomSheetBehavior;
    private ArrayList<Facedetectionmodel> facedetectionmodels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        facedetectionmodels=new ArrayList<>();
        bottomSheetBehavior=BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        imageView=findViewById(R.id.face_detection_image_view);
        facedetectioncameraview=findViewById(R.id.face_detection_camera_view);
        Button togggle=findViewById(R.id.face_detection_camera_toogle_button);
        FrameLayout bottomsheetbutton=findViewById(R.id.bottomsheetframelayout);
        bottomsheetRecyclerview =findViewById(R.id.bottom_sheet_button_recycler_view);

        facedetectioncameraview.setFacing(camerafacing);
        facedetectioncameraview.setLifecycleOwner(MainActivity.this);
        facedetectioncameraview.addFrameProcessor(MainActivity.this);
        togggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camerafacing=(camerafacing==Facing.FRONT)?Facing.BACK:Facing.FRONT;
                facedetectioncameraview.setFacing(camerafacing);
            }
        });

        bottomsheetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(MainActivity.this);
            }
        });

        bottomsheetRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        bottomsheetRecyclerview.setAdapter(new FacedetectionAdapter(facedetectionmodels,MainActivity.this));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                assert result != null;
                Uri imageuri=result.getUri();
                try {
                    analyseImage(MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private void analyseImage(final Bitmap bitmap) {
        if(bitmap==null){
            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();
            return;
        }
        imageView.setImageBitmap(null);
        facedetectionmodels.clear();
        Objects.requireNonNull(bottomsheetRecyclerview.getAdapter()).notifyDataSetChanged();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        showProgress();
        FirebaseVisionImage firebaseVisionImage=FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetectorOptions options=new FirebaseVisionFaceDetectorOptions.Builder().setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE).setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS).setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS).build();
        FirebaseVisionFaceDetector faceDetector = FirebaseVision.getInstance().getVisionFaceDetector(options);
        faceDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            @Override
            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                Bitmap mutableImage = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                detectFaces(firebaseVisionFaces,mutableImage);
                imageView.setImageBitmap(mutableImage);
                hideProgress();
                bottomsheetRecyclerview.getAdapter().notifyDataSetChanged();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                hideProgress();

            }
        });
    }

    private void detectFaces(List<FirebaseVisionFace> firebaseVisionFaces, Bitmap bitmap) {
        if(firebaseVisionFaces==null|| bitmap==null){
            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();
            return;
        }

        Canvas canvas=new Canvas(bitmap);

        Paint facePaint=new Paint();
        facePaint.setColor(Color.GREEN);
        facePaint.setStyle(Paint.Style.STROKE);
        facePaint.setStrokeWidth(5f);

        Paint faceTextPaint=new Paint();
        faceTextPaint.setColor(Color.BLUE);
        faceTextPaint.setTextSize(30f);
        faceTextPaint.setTypeface(Typeface.SANS_SERIF);

        Paint landmarkPaint =new Paint();
        landmarkPaint.setColor(Color.RED);
        landmarkPaint.setStyle(Paint.Style.FILL);
        landmarkPaint.setStrokeWidth(8f);

        for(int i=0;i<firebaseVisionFaces.size();i++){
            canvas.drawRect(firebaseVisionFaces.get(i).getBoundingBox(),facePaint);
            canvas.drawText("Face"+i,((firebaseVisionFaces.get(i).getBoundingBox().centerX())-(firebaseVisionFaces.get(i).getBoundingBox().width() >> 1)+8f),(firebaseVisionFaces.get(i).getBoundingBox().centerY()+(firebaseVisionFaces.get(i).getBoundingBox().height() >> 1)-8f),faceTextPaint);
            FirebaseVisionFace face=firebaseVisionFaces.get(i);
            if(face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE)!=null){
                FirebaseVisionFaceLandmark lefteye =face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
                assert lefteye != null;
                canvas.drawCircle(lefteye.getPosition().getX(),lefteye.getPosition().getY(),8f,landmarkPaint);
            }
            if(face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE)!=null){
                FirebaseVisionFaceLandmark righteye =face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE);
                assert righteye != null;
                canvas.drawCircle(righteye.getPosition().getX(),righteye.getPosition().getY(),8f,landmarkPaint);
            }
            if(face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!=null){
                FirebaseVisionFaceLandmark nose =face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE);
                assert nose != null;
                canvas.drawCircle(nose.getPosition().getX(),nose.getPosition().getY(),8f,landmarkPaint);
            }
            if(face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)!=null){
                FirebaseVisionFaceLandmark leftear =face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);
                assert leftear != null;
                canvas.drawCircle(leftear.getPosition().getX(),leftear.getPosition().getY(),8f,landmarkPaint);
            }
            if(face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)!=null){
                FirebaseVisionFaceLandmark rightear =face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR);
                assert rightear != null;
                canvas.drawCircle(rightear.getPosition().getX(),rightear.getPosition().getY(),8f,landmarkPaint);
            }
            if(face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT)!=null && face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT)!=null && face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM)!=null){
                FirebaseVisionFaceLandmark leftmouth =face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT);
                FirebaseVisionFaceLandmark rightmouth =face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT);
                FirebaseVisionFaceLandmark bottommouth =face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM);
                assert leftmouth != null;
                assert bottommouth != null;
                assert rightmouth != null;
                canvas.drawLine(leftmouth.getPosition().getX(),leftmouth.getPosition().getY(),bottommouth.getPosition().getX(),bottommouth.getPosition().getY(),landmarkPaint);
                canvas.drawLine(bottommouth.getPosition().getX(),bottommouth.getPosition().getY(),rightmouth.getPosition().getX(),rightmouth.getPosition().getY(),landmarkPaint);
            }
            facedetectionmodels.add(new Facedetectionmodel(i,"Smiling Probability"+face.getSmilingProbability()));
            facedetectionmodels.add(new Facedetectionmodel(i,"Left Eye Open Probability"+face.getLeftEyeOpenProbability()));
            facedetectionmodels.add(new Facedetectionmodel(i,"Right Eye Open Probability"+face.getRightEyeOpenProbability()));
        }
    }

    private void showProgress() {
        findViewById(R.id.bottomsheetbuttonimage).setVisibility(View.GONE);
        findViewById(R.id.bottom_sheet_button_progressbar).setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        findViewById(R.id.bottomsheetbuttonimage).setVisibility(View.VISIBLE);
        findViewById(R.id.bottom_sheet_button_progressbar).setVisibility(View.GONE);
    }

    @Override
    public void process(@NonNull Frame frame) {
        if(frame==null){
            Toast.makeText(this, "NULL OBJECT", Toast.LENGTH_SHORT).show();
            return;
        }
        final int width=frame.getSize().getWidth();
        final int height=frame.getSize().getHeight();
        FirebaseVisionImageMetadata metadata= new FirebaseVisionImageMetadata.Builder().setHeight(height).setWidth(width).setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21).setRotation((camerafacing==Facing.FRONT)?FirebaseVisionImageMetadata.ROTATION_270:FirebaseVisionImageMetadata.ROTATION_90).build();
        FirebaseVisionImage firebaseVisionImage =FirebaseVisionImage.fromByteArray(frame.getData(),metadata);
        FirebaseVisionFaceDetectorOptions options =new FirebaseVisionFaceDetectorOptions.Builder().setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS).build();
        FirebaseVisionFaceDetector faceDetector =FirebaseVision.getInstance().getVisionFaceDetector(options);
        faceDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            @Override
            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                imageView.setImageBitmap(null);
                Bitmap bitmap=Bitmap.createBitmap(height,width,Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Paint dotpaint =new Paint();
                dotpaint.setColor(Color.RED);
                dotpaint.setStyle(Paint.Style.FILL);
                dotpaint.setStrokeWidth(3f);

                Paint linePaint =new Paint();
                linePaint.setColor(Color.GREEN);
                linePaint.setStyle(Paint.Style.STROKE);
                linePaint.setStrokeWidth(2f);

                for(FirebaseVisionFace face:firebaseVisionFaces){
                    List<FirebaseVisionPoint> facecontours =face.getContour(FirebaseVisionFaceContour.FACE).getPoints();
                    for(int i=0;i<facecontours.size();i++){
                        FirebaseVisionPoint faceContoue= null;
                        if(i!=facecontours.size()-1){
                            faceContoue=facecontours.get(i);
                            canvas.drawLine(faceContoue.getX(),faceContoue.getY(),facecontours.get(i+1).getX(),facecontours.get(i+1).getY(),linePaint);

                        }
                        else {
                            canvas.drawLine(faceContoue.getX(),faceContoue.getY(),facecontours.get(0).getX(),facecontours.get(0).getY(),linePaint);
                        }
                        canvas.drawCircle(faceContoue.getX(),faceContoue.getY(),4f,dotpaint);
                    }
                    List<FirebaseVisionPoint> lefteyebrowtopcontours =face.getContour(FirebaseVisionFaceContour.LEFT_EYEBROW_TOP).getPoints();
                    for(int i=0;i<lefteyebrowtopcontours.size();i++){
                        FirebaseVisionPoint contour=lefteyebrowtopcontours.get(i) ;
                        if(i!=lefteyebrowtopcontours.size()-1){
                            canvas.drawLine(contour.getX(),contour.getY(),lefteyebrowtopcontours.get(i+1).getX(),lefteyebrowtopcontours.get(i+1).getY(),linePaint);
                        }
                        canvas.drawCircle(contour.getX(),contour.getY(),4f,dotpaint);
                    }
                    List<FirebaseVisionPoint> righteyebrowtopcontours =face.getContour(FirebaseVisionFaceContour.RIGHT_EYEBROW_TOP).getPoints();
                    for(int i=0;i<righteyebrowtopcontours.size();i++){
                        FirebaseVisionPoint contour=righteyebrowtopcontours.get(i) ;
                        if(i!=righteyebrowtopcontours.size()-1){
                            canvas.drawLine(contour.getX(),contour.getY(),righteyebrowtopcontours.get(i+1).getX(),righteyebrowtopcontours.get(i+1).getY(),linePaint);
                        }
                        canvas.drawCircle(contour.getX(),contour.getY(),4f,dotpaint);
                    }
                    List<FirebaseVisionPoint> rightEyebrowBottomCountours = face.getContour(
                            FirebaseVisionFaceContour. RIGHT_EYEBROW_BOTTOM).getPoints();
                    for (int i = 0; i < rightEyebrowBottomCountours.size(); i++) {
                        FirebaseVisionPoint contour = rightEyebrowBottomCountours.get(i);
                        if (i != (rightEyebrowBottomCountours.size() - 1))
                            canvas.drawLine(contour.getX(), contour.getY(), rightEyebrowBottomCountours.get(i + 1).getX(),rightEyebrowBottomCountours.get(i + 1).getY(), linePaint);
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f,dotpaint);

                    }

                    List<FirebaseVisionPoint> leftEyeContours = face.getContour(
                            FirebaseVisionFaceContour.LEFT_EYE).getPoints();
                    for (int i = 0; i < leftEyeContours.size(); i++) {
                        FirebaseVisionPoint contour = leftEyeContours.get(i);
                        if (i != (leftEyeContours.size() - 1)){
                            canvas.drawLine(contour.getX(), contour.getY(), leftEyeContours.get(i + 1).getX(),leftEyeContours.get(i + 1).getY(), linePaint);

                        }else {
                            canvas.drawLine(contour.getX(), contour.getY(), leftEyeContours.get(0).getX(),
                                    leftEyeContours.get(0).getY(), linePaint);
                        }
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f, dotpaint);


                    }

                    List<FirebaseVisionPoint> rightEyeContours = face.getContour(
                            FirebaseVisionFaceContour.RIGHT_EYE).getPoints();
                    for (int i = 0; i < rightEyeContours.size(); i++) {
                        FirebaseVisionPoint contour = rightEyeContours.get(i);
                        if (i != (rightEyeContours.size() - 1)){
                            canvas.drawLine(contour.getX(), contour.getY(), rightEyeContours.get(i + 1).getX(),rightEyeContours.get(i + 1).getY(), linePaint);

                        }else {
                            canvas.drawLine(contour.getX(), contour.getY(), rightEyeContours.get(0).getX(),
                                    rightEyeContours.get(0).getY(), linePaint);
                        }
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f, dotpaint);


                    }

                    List<FirebaseVisionPoint> upperLipTopContour = face.getContour(
                            FirebaseVisionFaceContour.UPPER_LIP_TOP).getPoints();
                    for (int i = 0; i < upperLipTopContour.size(); i++) {
                        FirebaseVisionPoint contour = upperLipTopContour.get(i);
                        if (i != (upperLipTopContour.size() - 1)){
                            canvas.drawLine(contour.getX(), contour.getY(),
                                    upperLipTopContour.get(i + 1).getX(),
                                    upperLipTopContour.get(i + 1).getY(), linePaint);
                        }
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f, dotpaint);

                    }

                    List<FirebaseVisionPoint> upperLipBottomContour = face.getContour(
                            FirebaseVisionFaceContour.UPPER_LIP_BOTTOM).getPoints();
                    for (int i = 0; i < upperLipBottomContour.size(); i++) {
                        FirebaseVisionPoint contour = upperLipBottomContour.get(i);
                        if (i != (upperLipBottomContour.size() - 1)){
                            canvas.drawLine(contour.getX(), contour.getY(), upperLipBottomContour.get(i + 1).getX(),upperLipBottomContour.get(i + 1).getY(), linePaint);
                        }
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f, dotpaint);

                    }
                    List<FirebaseVisionPoint> lowerLipTopContour = face.getContour(
                            FirebaseVisionFaceContour.LOWER_LIP_TOP).getPoints();
                    for (int i = 0; i < lowerLipTopContour.size(); i++) {
                        FirebaseVisionPoint contour = lowerLipTopContour.get(i);
                        if (i != (lowerLipTopContour.size() - 1)){
                            canvas.drawLine(contour.getX(), contour.getY(), lowerLipTopContour.get(i + 1).getX(),lowerLipTopContour.get(i + 1).getY(), linePaint);
                        }
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f, dotpaint);

                    }
                    List<FirebaseVisionPoint> lowerLipBottomContour = face.getContour(
                            FirebaseVisionFaceContour.LOWER_LIP_BOTTOM).getPoints();
                    for (int i = 0; i < lowerLipBottomContour.size(); i++) {
                        FirebaseVisionPoint contour = lowerLipBottomContour.get(i);
                        if (i != (lowerLipBottomContour.size() - 1)){
                            canvas.drawLine(contour.getX(), contour.getY(), lowerLipBottomContour.get(i + 1).getX(),lowerLipBottomContour.get(i + 1).getY(), linePaint);
                        }
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f, dotpaint);

                    }

                    List<FirebaseVisionPoint> noseBridgeContours = face.getContour(
                            FirebaseVisionFaceContour.NOSE_BRIDGE).getPoints();
                    for (int i = 0; i < noseBridgeContours.size(); i++) {
                        FirebaseVisionPoint contour = noseBridgeContours.get(i);
                        if (i != (noseBridgeContours.size() - 1)) {
                            canvas.drawLine(contour.getX(), contour.getY(), noseBridgeContours.get(i + 1).getX(),noseBridgeContours.get(i + 1).getY(), linePaint);
                        }
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f, dotpaint);

                    }

                    List<FirebaseVisionPoint> noseBottomContours = face.getContour(
                            FirebaseVisionFaceContour.NOSE_BOTTOM).getPoints();
                    for (int i = 0; i < noseBottomContours.size(); i++) {
                        FirebaseVisionPoint contour = noseBottomContours.get(i);
                        if (i != (noseBottomContours.size() - 1)) {
                            canvas.drawLine(contour.getX(), contour.getY(), noseBottomContours.get(i + 1).getX(),noseBottomContours.get(i + 1).getY(), linePaint);
                        }
                        canvas.drawCircle(contour.getX(), contour.getY(), 4f, dotpaint);
                }
                    if (camerafacing == Facing.FRONT) {
                        //Flip image!
                        Matrix matrix = new Matrix();
                        matrix.preScale(-1f, 1f);
                        Bitmap flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                                bitmap.getWidth(), bitmap.getHeight(),
                                matrix, true);
                        imageView.setImageBitmap(flippedBitmap);
                    }else
                        imageView.setImageBitmap(bitmap);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imageView.setImageBitmap(null);

            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
