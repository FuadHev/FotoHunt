package com.fuadhev.fotohunt.ui.view.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.fuadhev.fotohunt.R
import com.fuadhev.fotohunt.common.downloader.AndroidDownloader
import com.fuadhev.fotohunt.databinding.FragmentFotoDetailBinding
import com.fuadhev.fotohunt.model.Hit
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FotoDetailFragment : Fragment() {

    private lateinit var binding: FragmentFotoDetailBinding
    private lateinit var permissionResultLauncher: ActivityResultLauncher<String>
    private val args by navArgs<FotoDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_foto_detail, container, false)

        return binding.root
    }

    private var selectedItemIndexed = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageInfo = args.imageInfo
        registerLauncher()
        binding.fotoDetailFragment=this
        binding.imageInfo=imageInfo
        Picasso.get().load(imageInfo.largeImageURL).into(binding.image)


    }
     fun cliclDownloadBtn(imageInfo:Hit){

         if (ContextCompat.checkSelfPermission(
                 requireActivity(),
                 android.Manifest.permission.WRITE_EXTERNAL_STORAGE
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             if (ActivityCompat.shouldShowRequestPermissionRationale(
                     requireActivity(),
                     android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                 )
             ) {
                 permissionResultLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

             } else {
                 permissionResultLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
             }
         } else {
             setDownloadDialog(imageInfo)
         }

    }

    private fun setDownloadDialog(imageInfo: Hit){

        val webFormat = "${imageInfo.webformatWidth}x${imageInfo.webformatHeight}"
        val previewFormat = "${imageInfo.previewWidth}x${imageInfo.previewHeight}"
        val largeFormat = "${imageInfo.imageWidth}x${imageInfo.imageHeight}"
        val downloader = AndroidDownloader(requireActivity())
        val selections = arrayOf(previewFormat, webFormat, largeFormat)

        var selectedResolution = selections[selectedItemIndexed]

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select resolution")
            .setSingleChoiceItems(selections, selectedItemIndexed) { dialog, which ->
                selectedItemIndexed = which
                selectedResolution = selections[which]
            }
            .setPositiveButton("Download") { dialog, which ->
                Toast.makeText(
                    requireContext(),
                    "Download started",
                    Toast.LENGTH_SHORT
                ).show()
                when (selectedResolution) {

                    webFormat -> {
                        downloader.downloadFile(imageInfo.webformatURL)
                    }
                    previewFormat -> {
                        downloader.downloadFile(imageInfo.previewURL)
                    }
                    largeFormat -> {
                        downloader.downloadFile(imageInfo.largeImageURL)
                    }
                }
            }
            .show()
    }

    private fun registerLauncher(){
            permissionResultLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                    if (it) {
                        Toast.makeText(requireContext(), "Permission Garanted", Toast.LENGTH_SHORT).show()

                    } else {

                        Toast.makeText(requireContext(), "Permission needed", Toast.LENGTH_SHORT).show()

                    }


                }

    }
}